package yil712.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import yil712.element.Investment;

public class SymbolControl {
	private Connection con = null;
	private SimpleDateFormat stdDate1 = new SimpleDateFormat("yyyy/MM/dd:hh/mm/ssa");
	private SimpleDateFormat stdDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SymbolControl() {
		con = DBConnection.getConnection();
	}
	
	public int getInvestType(String symbol) {
		// 0 - stokcs, 1 - mutual funds
		if (symbol.length() >= 5 && !symbol.contains("-")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public ArrayList<Investment> readSymbolFromFile(String fileName) throws FileNotFoundException {
		ArrayList<Investment> tickerList = new ArrayList<Investment>();
		File file = new File(fileName);

		Scanner reader = new Scanner(file);
		reader.useDelimiter(",|\\n");
		
		String line;
		Scanner parser;
		Date date;

		while (reader.hasNext()) {
			line = reader.next();
			parser = new Scanner(line.trim());
			if (parser.hasNext()) {
				//System.out.println(line.trim());
				if (parser.hasNext()) {
					String symbol = parser.next();
					try {
						if (parser.hasNext()) {
							date = stdDate1.parse(parser.next());
							Timestamp time = Timestamp.valueOf(stdDate2.format(date));
							if (parser.hasNext()) {
								double price = Double.parseDouble(parser.next());
								// System.out.println(symbol + ", " +
								// time.toString() + ", " + price);
								Investment tempInvest = new Investment(symbol, time, price);
								tickerList.add(tempInvest);
							}
						}
					} catch (ParseException e) {
						// just jump this record
					}
				}
			}
		}

		reader.close();
		return tickerList;
	}
	
	public boolean isSymbolExisted(String symbol) {
		String query = "select invest_symbol from investment where invest_symbol = '" + symbol + "'";
		boolean flag = false;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				flag = true;
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	private Timestamp getLatestTimestamp(String symbol, String typeTable) {
		String query = "select time from " + typeTable + " where symbol = '" + symbol + "'";
		Timestamp time = null;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				time = Timestamp.valueOf(result.getString("time"));
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public boolean updateOneSymbol(Investment oneInvestment) {
		boolean flag = false;
		int type = getInvestType(oneInvestment.getSymbol());
		String typeTable;
		
		if (type == 0) {
			typeTable = "stock";
		} else {
			typeTable = "mutual_fund";
		}
		
		Timestamp stamp = getLatestTimestamp(oneInvestment.getSymbol(), typeTable);
		if (stamp != null) {// if existed, update it
//update stock set time = TO_TIMESTAMP('2014-04-01 22:05:00.0', 'YYYY-MM-DD HH24:MI:SS.FF'), price = 32.33 where tiker_symbol = 'AAN'
			if (stamp.before(oneInvestment.getTimestamp())) {
				String query = "update " + typeTable + " set time = TO_TIMESTAMP('" + oneInvestment.getTimestamp().toString() + "', 'YYYY-MM-DD HH24:MI:SS.FF'), " +
						"price = '" + oneInvestment.getPrice() + "' where symbol = '" + oneInvestment.getSymbol() + "'";
				
				Statement s;
				try {
					s = con.createStatement();
					
					ResultSet result;
					
					result = s.executeQuery(query);		
					if (result.next()) {
						flag = true;
					}
					
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				flag = true; // the input is in fact older...
			}
		} else {
//insert into mutual_fund values('AABNX', TO_TIMESTAMP('2014-04-01 22:05:00.0', 'YYYY-MM-DD HH24:MI:SS.FF'), 12.50)
			String query1 = "insert into investment values ('" + oneInvestment.getSymbol() + "', '" + type + "')";
			String query2 = "insert into " + typeTable + " values('" + oneInvestment.getSymbol() + "', TO_TIMESTAMP('" + oneInvestment.getTimestamp().toString() +
								"', 'YYYY-MM-DD HH24:MI:SS.FF'), '" + oneInvestment.getPrice() + "')";
			Statement s;
			try {
				con.setAutoCommit(false);
				s = con.createStatement();
				
				int result1, result2;
				result1 = s.executeUpdate(query1);
				result2 = s.executeUpdate(query2);
				
				if (result1 > 0 && result2 > 0) {
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
		}

		return flag;
	}
	
	public boolean updateSymbol(ArrayList<Investment> investList) {
		for (Investment oneInvest : investList) {
			if (!updateOneSymbol(oneInvest)) {
				return false;
			}
		}
		return true;
	}
	
	
	public double getCrtPrice(String symbol, int type) {
		String typeTable;
		
		if (type == 0) {
			typeTable = "stock";
		} else {
			typeTable = "mutual_fund";
		}
		
		String query = "select price from " + typeTable + " where symbol = '" + symbol + "'";
		double price = 0;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);		
			if (result.next()) {
				price = Double.parseDouble(result.getString("price"));
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return price;
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		SymbolControl control = new SymbolControl();
//		ArrayList<Investment> tickerList = control.readSymbolFromFile("data/ticker1.txt");
//		for (Investment invest : tickerList) {
//			System.out.println(invest.getSymbol() + ", " + invest.getTimestamp() + ", " + invest.getPrice());
//		}
		
//		System.out.println(control.isSymbolExisted("AAN"));
		
//		String symbol = "AAAUX";
//		Timestamp time = Timestamp.valueOf("2014-04-01 22:05:00.0");
//		double price = 25.25;
//		Investment tempInvest = new Investment(symbol, time, price);
//		System.out.println(control.updateOneSymbol(tempInvest));
		
		ArrayList<Investment> tickerList = control.readSymbolFromFile("data/ticker1.txt");
		control.updateSymbol(tickerList);
		
//		System.out.println(control.getNewestTimestamp("AAN", "stock"));
	}
}
