package yil712.control;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import yil712.element.Investment;

public class InvestAcctControl {
	private Connection con = null;
	
	public InvestAcctControl() {
		con = DBConnection.getConnection();
	}
	
	public ArrayList<Investment> getAllStocks() {
		String query = "select * from stock";
		ArrayList<Investment> stockList = new ArrayList<Investment>();
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			while (result.next()) {
				String symbol = result.getString("SYMBOL");
				Timestamp time = result.getTimestamp("TIME");
				double price = result.getDouble("PRICE");
				Investment tempStock = new Investment(symbol, time, price);
				stockList.add(tempStock);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stockList;
	}
	
	public ArrayList<Investment> getAllStocksOfOneAccount(String accountID) {
//		select * from 
//		(select account_num, invest_symbol as symbol, sum(number_of_shares) as num from account_investment
//		where account_num = 'A000006' and number_of_shares > '0'
//		group by account_num, invest_symbol) natural join stock
		String query = "select * from (select account_num, invest_symbol as symbol, sum(number_of_shares) as num from account_investment " +
						"where account_num = '" + accountID + "' and number_of_shares > '0' group by account_num, invest_symbol) natural join stock";
		ArrayList<Investment> stockList = new ArrayList<Investment>();
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			while (result.next()) {
				String symbol = result.getString("SYMBOL");
				Timestamp time = result.getTimestamp("TIME");
				double price = result.getDouble("PRICE");
				Investment tempStock = new Investment(symbol, time, price);
				stockList.add(tempStock);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stockList;
	}
	
	public ArrayList<Investment> getAllMutualFunds() {
		String query = "select * from mutual_fund";
		ArrayList<Investment> fundList = new ArrayList<Investment>();
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			while (result.next()) {
				String symbol = result.getString("SYMBOL");
				Timestamp time = result.getTimestamp("TIME");
				double price = result.getDouble("PRICE");
				Investment tempStock = new Investment(symbol, time, price);
				fundList.add(tempStock);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fundList;
	}
	
	public ArrayList<Investment> getAllMutualFundsOfOneAccount(String accountID) {
//		select * from 
//		(select account_num, invest_symbol as symbol, sum(number_of_shares) as num from account_investment
//		where account_num = 'A000006' and number_of_shares > '0'
//		group by account_num, invest_symbol) natural join stock
		String query = "select * from (select account_num, invest_symbol as symbol, sum(number_of_shares) as num from account_investment " +
						"where account_num = '" + accountID + "' and number_of_shares > '0' group by account_num, invest_symbol) natural join mutual_fund";
		ArrayList<Investment> stockList = new ArrayList<Investment>();
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			while (result.next()) {
				String symbol = result.getString("SYMBOL");
				Timestamp time = result.getTimestamp("TIME");
				double price = result.getDouble("PRICE");
				Investment tempStock = new Investment(symbol, time, price);
				stockList.add(tempStock);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stockList;
	}
	
	public boolean buyStocksOrFunds(String accountID, String investSymbol, int type, int numberOfShares) {
//		insert into account_investment values ('A000006', 'VC', TO_TIMESTAMP('2014-04-07 22:05:00.0', 'YYYY-MM-DD HH24:MI:SS.FF'), '8.45', '100');
//		insert into transaction values ('T000045', 'A000006', '845', TO_DATE('2014-04-07','YYYY-MM-DD'), '6');
//		insert into invest_transaction values ('T000045', 'VC', 100)
		
		SymbolControl control1 = new SymbolControl();
		double price = control1.getCrtPrice(investSymbol, type);
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    Timestamp time = new Timestamp(utilDate.getTime());
	    double amount = price * numberOfShares;
	    TransControl control2 = new TransControl();
	    String newTransID = Utility.getNewID(control2.getLatestTransID());
	    
	    String query0 = "insert into account_investment values ('" + accountID + "', '" + investSymbol + "', TO_TIMESTAMP('" + time + 
	    				"', 'YYYY-MM-DD HH24:MI:SS.FF'), '" + price + "', '" + numberOfShares + "')";
		String query1 = "insert into transaction values('" + newTransID + "', '" + accountID + 
							"', '" + amount + "', TO_DATE('" + sqlDate + "','YYYY-MM-DD'), '6')";
		String query2 = "insert into invest_transaction values('" + newTransID + "', '" + investSymbol + "', '" + numberOfShares+ "', '0', '-1')";
		
		boolean flag = false;
		
//		System.out.println(query0);
//		System.out.println(query1);
//		System.out.println(query2);

		Statement s;
		try {
			con.setAutoCommit(false);
			s = con.createStatement();
			
			int result0, result1, result2;
			result0 = s.executeUpdate(query0);
			result1 = s.executeUpdate(query1);
			result2 = s.executeUpdate(query2);
			
			if (result0 > 0 && result1 > 0 && result2 > 0) {
				flag = true;
			}			
			
			s.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public Timestamp getEarlestTimestamp(String accountID, String investSymbol) {
		String query = "select min(time) from (select * from account_investment where account_num = '" + accountID + "' and invest_symbol = '" +
						investSymbol + "' and number_of_shares > '0')";
		Timestamp time = null;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				time = Timestamp.valueOf(result.getString(1));
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public boolean sellStocksOrFunds(String accountID, String investSymbol, int type, int numberOfShares) {
		int dueNumber = numberOfShares;
		boolean flag = true;
		
		Timestamp targetTime, todayTime;
		int ownNumber, toSell, shortOrLong;
		double crtPrice, amount, prevPrice, gainOrLoss;
		Statement s;
		
		TransControl control = new TransControl();
		SymbolControl control2 = new SymbolControl();
		
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    todayTime = new Timestamp(utilDate.getTime());
	    
	    crtPrice = control2.getCrtPrice(investSymbol, type);
	    
	    try {
			con.setAutoCommit(false);
			s = con.createStatement();
	    
			while (dueNumber > 0) {
				targetTime = getEarlestTimestamp(accountID, investSymbol);
				ownNumber = getRecordNumOfShares(accountID, investSymbol, targetTime);
				prevPrice = getRecordPrice(accountID, investSymbol, targetTime);
				
				if (ownNumber >= dueNumber) {
					toSell = dueNumber;
				} else {
					toSell = ownNumber;
				}
				dueNumber = dueNumber - toSell;
				ownNumber = ownNumber - toSell;
	//			update account_investment set number_of_shares = '90' where account_num = 'A000006' and invest_symbol = 'VC' and time = TO_TIMESTAMP('2014-04-07 22:05:00.0', 'YYYY-MM-DD HH24:MI:SS.FF');
	//			insert into transaction values ('T000049', 'A000006', '84.5', TO_DATE('2014-04-10','YYYY-MM-DD'), '7');
	//			insert into invest_transaction values ('T000049', 'VC', '10')
				amount = crtPrice * toSell;
				
				gainOrLoss = (crtPrice - prevPrice) * toSell; // check the gain or loss for the tax statement
				if (Utility.isLongTerm(targetTime, todayTime)) {
					shortOrLong = 1; // long term
				} else {
					shortOrLong = 0; // short term
				}
						
			    String newTransID = Utility.getNewID(control.getLatestTransID());
			    
				String query0 = "update account_investment set number_of_shares = '" + ownNumber + "' where account_num = '" + accountID + "' and invest_symbol = '" +
									investSymbol + "' and time = TO_TIMESTAMP('" + targetTime + "', 'YYYY-MM-DD HH24:MI:SS.FF')";
				String query1 = "insert into transaction values('" + newTransID + "', '" + accountID + 
									"', '" + amount + "', TO_DATE('" + sqlDate + "','YYYY-MM-DD'), '7')";
				String query2 = "insert into invest_transaction values('" + newTransID + "', '" + investSymbol + "', '" + toSell+ "', '" + 
									gainOrLoss + "', '" + shortOrLong + "')";
	
//				System.out.println(query0);
//				System.out.println(query1);
//				System.out.println(query2);
					
				int result0, result1, result2;
				result0 = s.executeUpdate(query0);
				result1 = s.executeUpdate(query1);
				result2 = s.executeUpdate(query2);
					
				if (!(result0 > 0 && result1 > 0 && result2 > 0)) {
					flag = false;
					break;
				}			
			}// end while
			
			s.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			flag = false;
		}
		
		return flag;
	}
	
	public double getRecordPrice(String accountID, String investSymbol, Timestamp time) {
		String query = "select price from account_investment where account_num = '" + accountID + "' and invest_symbol = '" +
				investSymbol + "' and time = TO_TIMESTAMP('" + time + "', 'YYYY-MM-DD HH24:MI:SS.FF')";
		double price = 0;
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				price = result.getDouble("price");
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}
	
	public int getRecordNumOfShares(String accountID, String investSymbol, Timestamp time) {
//select number_of_shares from account_investment where account_num = 'A000006' and invest_symbol = 'VC' and time = TO_TIMESTAMP('2014-04-07 22:05:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
		String query = "select number_of_shares from account_investment where account_num = '" + accountID + "' and invest_symbol = '" +
						investSymbol + "' and time = TO_TIMESTAMP('" + time + "', 'YYYY-MM-DD HH24:MI:SS.FF')";
		int number = 0;
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				number = result.getInt("number_of_shares");
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return number;
	}
	
	public int getCurrentNumOfShares(String accountID, String investSymbol) {
//		select account_num, invest_symbol, sum(number_of_shares) as num from account_investment
//		where account_num = 'A000006' and invest_symbol = 'VC' and number_of_shares > '0'
//		group by account_num, invest_symbol
		
		String query = "select account_num, invest_symbol, sum(number_of_shares) as num from account_investment " +
						"where account_num = '" + accountID + "' and invest_symbol = '" + investSymbol + "' and number_of_shares > '0' " +
						"group by account_num, invest_symbol";
		int number = 0;
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				number = result.getInt("num");
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return number;
	}
	
	public double getInvestAccountBalance(String accountID) {
		String query = "select balance from invest_account where account_num = '" + accountID + "'";
		double balance = 0;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				balance = result.getDouble("BALANCE");
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		InvestAcctControl control = new InvestAcctControl();
		System.out.println(control.buyStocksOrFunds("A000006", "AAN", 0, 5));
		//System.out.println(control.sellStocksOrFunds("A000006", "VC", 0, 10));
	}
}
