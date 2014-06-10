package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProgramTrading {
	
	public static void outputTransactions(String accountID) {
		Connection con = DBConnection.getConnection();
		String query = "select INVEST_SYMBOL, TRANSACTION_TYPE, NUM_OF_SHARES from transaction " +
						"natural join invest_transaction where account_num = '" + accountID + "' order by transaction_date";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			while (result.next()) {
				String symbol = result.getString("INVEST_SYMBOL").trim();
				String type = result.getInt("TRANSACTION_TYPE") == 6? "buy":"sell";
				int number = result.getInt("NUM_OF_SHARES");
				System.out.println(symbol + ", " + type + ", " + number);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void executeTransactions(String accountID) {
		Scanner reader = new Scanner(System.in);
		String trade, symbol, action;
		int buyOrSell, numOfShares;
		Scanner parser;

		int line = 0;
		while (reader.hasNextLine()) {
			trade = reader.nextLine();
			line ++;
			if (trade.isEmpty()) {
				break;
			} else {
				parser = new Scanner(trade);
				parser.useDelimiter(", *");
				if (parser.hasNext()) {
					symbol = parser.next();
					if (parser.hasNext()) {
						action = parser.next();
						if (action.equals("buy") || action.equals("sell")) {
							if (action.equals("sell")) {
								buyOrSell = 1; // sell
							} else {
								buyOrSell = 0; // buy
							}
							if (parser.hasNext()) {
								try {
									numOfShares = Integer.parseInt(parser.next());
									InvestAcctControl control = new InvestAcctControl();
									SymbolControl control1 = new SymbolControl();
									int symbolType = control1.getInvestType(symbol);
									
									if (buyOrSell == 0) { // to buy
										double bal = control.getInvestAccountBalance(accountID);
										double crtPrice = control1.getCrtPrice(symbol, symbolType);
										if (crtPrice * numOfShares <= bal) { // can buy
//											System.out.println("Line " + line + ": " + "Buy action succeed.");
											
											if (control.buyStocksOrFunds(accountID, symbol, symbolType, numOfShares)) {
												System.out.println("Line " + line + ": " + "Buy action succeed.");
											} else {
												System.out.println("Line " + line + ": " + "Buy action failed, database exception.");
											}
										} else {
											System.out.println("Line " + line + ": " + "Buy action failed, insufficient balance.");
										}
									} else if (buyOrSell == 1) { // to sell
										int heldNum = control.getCurrentNumOfShares(accountID, symbol);
										if (numOfShares <= heldNum) { // can sell
//											System.out.println("Line " + line + ": " + "Sell action succeed.");
											
											if (control.sellStocksOrFunds(accountID, symbol, symbolType, numOfShares)) {
												System.out.println("Line " + line + ": " + "Sell action succeed.");
											} else {
												System.out.println("Line " + line + ": " + "Sell action failed, database exception.");
											}
										} else {
											System.out.println("Line " + line + ": " + "Sell action failed, insufficient held shares.");
										}
									}
								} catch (NumberFormatException e) {
									System.out.println("Line " + line + ": " + "Incorrect format of numOfShares.");
								}
							} else {
								System.out.println("Line " + line + ": " + "Incomplete trade action.");
							}
						} else {
							System.out.println("Line " + line + ": " + "Unknown trade action.");
						}
					} else {
						System.out.println("Line " + line + ": " + "Incomplete trade action.");
					}
				} else {
					System.out.println("Line " + line + ": " + "Incomplete trade action.");
				}
				
			}
		}
	}
}
