package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CashAcctControl {
	private Connection con = null;
	
	public CashAcctControl() {
		con = DBConnection.getConnection();
	}
	
	public double getCashAccountBalance(String accountID) {
		String query = "select balance from cash_account where account_num = '" + accountID + "'";
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
		
	public boolean depositTo(String accountID, double amount) {
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    
		    TransControl control = new TransControl();
		    String newTransID = Utility.getNewID(control.getLatestTransID());
		    
//			insert into transaction values('T000003', 'A000005', '250', '16-Apr-2014', '1');
//			insert into cash_transaction values('T000003');
		    // with a trigger - cashTransaction
		    
			String query1 = "insert into transaction values('" + newTransID + "', '" + accountID + 
								"', '" + amount + "', TO_DATE('" + sqlDate + "','YYYY-MM-DD'), '1')";
			String query2 = "insert into cash_transaction values('" + newTransID + "')";
			
			boolean flag = false;
			
//			System.out.println(query1);
//			System.out.println(query2);

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
			
			return flag;
		}
	
	public boolean withdrawalFrom(String accountID, double amount) {
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
	    TransControl control = new TransControl();
	    String newTransID = Utility.getNewID(control.getLatestTransID());
	    
		String query1 = "insert into transaction values('" + newTransID + "', '" + accountID + 
							"', '" + amount + "', TO_DATE('" + sqlDate + "','YYYY-MM-DD'), '2')";
		String query2 = "insert into cash_transaction values('" + newTransID + "')";
		
		boolean flag = false;
		
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
		
		return flag;
	}
	
	public static void main(String args[]) {
		CashAcctControl cashControl = new CashAcctControl(); 
		System.out.println(cashControl.withdrawalFrom("A000001", 1000));
	}
}
