package yil712.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yil712.element.Transaction;

public class TransControl {
	private Connection con = null;
	
	public TransControl() {
		con = DBConnection.getConnection();
	}
	
	public String getLatestTransID() {
		String transID = null;
		String query = "select max(transaction_id) from transaction";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				transID = result.getString(1);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (transID == null) {
			transID = "T000001";
		}
		return transID;
	}
	
	/**
	 * The transaction of money transfer
	 */
	public boolean transferFromTo(String fromAccount, int fromType, String toAccount, int toType, double amount) {
		
//		insert into transaction values('T000019', 'A000001', '50', '16-Apr-2014', '3');
//		insert into transfer_transaction values('T000019', 'A000005');
//		update cash_account set balance = balance - 50 where account_num = 'A000001';
//		update cash_account set balance = balance + 50 where account_num = 'A000005';
		
		// type: 1 - cash, 2 - invest, 3 - retire
		int taxable = 0;
		String fromTable = null;
		switch (fromType) {
			case 1: fromTable = "cash_account";
					break;
			case 2: fromTable = "invest_account";
					break;
			case 3: fromTable = "retire_account";
					taxable = 1;
					amount = amount * (1 - 0.25);
					break;
		}
		
		String toTable = null;
		switch (toType) {
			case 1: toTable = "cash_account";
					break;
			case 2: toTable = "invest_account";
					break;
			case 3: toTable = "retire_account";
					break;
		}
		
		
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
	    TransControl control = new TransControl();
	    String newTransID = Utility.getNewID(control.getLatestTransID());
		
		String query1 = "insert into transaction values('" + newTransID + "', '" + fromAccount + 
							"', '" + amount + "', TO_DATE('" + sqlDate + "','YYYY-MM-DD'), '3')";
		String query2 = "insert into transfer_transaction values('" + newTransID + "', '" + toAccount + "', '" + taxable + "')";
		String query3 = "update " + fromTable + " set balance = balance - " + amount + " where account_num = '" + fromAccount + "'";
		String query4 = "update " + toTable + " set balance = balance + " + amount + " where account_num = '" + toAccount + "'";
		
		boolean flag = false;
		
//		System.out.println(query1);
//		System.out.println(query2);
//		System.out.println(query3);
//		System.out.println(query4);
		
		Statement s;
		try {
		con.setAutoCommit(false);
		s = con.createStatement();
		
		int result1, result2, result3, result4;
		result1 = s.executeUpdate(query1);
		result2 = s.executeUpdate(query2);
		result3 = s.executeUpdate(query3);
		result4 = s.executeUpdate(query4);
		
		if (result1 > 0 && result2 > 0 && result3 > 0 && result4 > 0) {
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
	
	public ArrayList<Transaction> getCashAcctTransactions(String accountID) {
		ArrayList<Transaction> cashTransactions = new ArrayList<Transaction>();
		String query1 = "select * from transaction natural join cash_transaction where account_num = '" + accountID +"'";
		String query2 = "select * from transaction natural join transfer_transaction where account_num = '" + accountID +"'";
		String query3 = "select * from transaction natural join loan_transaction where account_num = '" + accountID +"'";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query1);
			while (result.next()) {
				Date date = result.getDate("TRANSACTION_DATE");
				int type = result.getInt("TRANSACTION_TYPE");
				String receiver = "N/A";
				double amount = result.getDouble("AMOUNT");
				
				Transaction tempTrans = new Transaction(date, type, receiver, amount);
				cashTransactions.add(tempTrans);
			}
			
			result = s.executeQuery(query2);
			while (result.next()) {
				Date date = result.getDate("TRANSACTION_DATE");
				int type = result.getInt("TRANSACTION_TYPE");
				String receiver = result.getString("TO_ACCOUNT_NUM");
				double amount = result.getDouble("AMOUNT");
				
				Transaction tempTrans = new Transaction(date, type, receiver, amount);
				cashTransactions.add(tempTrans);
			}
			
			result = s.executeQuery(query3);
			while (result.next()) {
				Date date = result.getDate("TRANSACTION_DATE");
				int type = result.getInt("TRANSACTION_TYPE");
				String receiver = result.getString("LOAN_ID");
				double amount = result.getDouble("AMOUNT");
				
				Transaction tempTrans = new Transaction(date, type, receiver, amount);
				cashTransactions.add(tempTrans);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cashTransactions;
	}
	
	public ArrayList<Transaction> getInvestAcctTransactions(String accountID) {
		ArrayList<Transaction> investTransactions = new ArrayList<Transaction>();
		String query1 = "select * from transaction natural join transfer_transaction where account_num = '" + accountID +"'";
		String query2 = "select * from transaction natural join invest_transaction where account_num = '" + accountID +"'";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
					
			result = s.executeQuery(query1);
			while (result.next()) {
				Date date = result.getDate("TRANSACTION_DATE");
				int type = result.getInt("TRANSACTION_TYPE");
				String receiver = result.getString("TO_ACCOUNT_NUM");
				double amount = result.getDouble("AMOUNT");
				
				Transaction tempTrans = new Transaction(date, type, receiver, amount);
				investTransactions.add(tempTrans);
			}
			
			result = s.executeQuery(query2);
			while (result.next()) {
				Date date = result.getDate("TRANSACTION_DATE");
				int type = result.getInt("TRANSACTION_TYPE");
				String receiver = result.getString("INVEST_SYMBOL");
				double amount = result.getDouble("AMOUNT");
				
				Transaction tempTrans = new Transaction(date, type, receiver, amount);
				investTransactions.add(tempTrans);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return investTransactions;
	}
	
	public ArrayList<Transaction> getRetireAcctTransactions(String accountID) {
		ArrayList<Transaction> retireTransactions = new ArrayList<Transaction>();
		String query1 = "select * from transaction natural join transfer_transaction where account_num = '" + accountID +"'";
		String query2 = "select * from transaction natural join invest_transaction where account_num = '" + accountID +"'";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
					
			result = s.executeQuery(query1);
			while (result.next()) {
				Date date = result.getDate("TRANSACTION_DATE");
				int type = result.getInt("TRANSACTION_TYPE");
				String receiver = result.getString("TO_ACCOUNT_NUM");
				double amount = result.getDouble("AMOUNT");
				
				Transaction tempTrans = new Transaction(date, type, receiver, amount);
				retireTransactions.add(tempTrans);
			}
			
			result = s.executeQuery(query2);
			while (result.next()) {
				Date date = result.getDate("TRANSACTION_DATE");
				int type = result.getInt("TRANSACTION_TYPE");
				String receiver = result.getString("INVEST_SYMBOL");
				double amount = result.getDouble("AMOUNT");
				
				Transaction tempTrans = new Transaction(date, type, receiver, amount);
				retireTransactions.add(tempTrans);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retireTransactions;
	}
	
	public static void main(String args[]) {
//		TransControl control = new TransControl();
//		System.out.println(control.transferFromTo("A000003", 3, "A000006", 2, 1050));
	}
}
