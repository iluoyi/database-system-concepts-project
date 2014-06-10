package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import yil712.element.AccountLoan;
import yil712.element.Loan;

public class LoanControl {
	private Connection con = null;
	
	public LoanControl() {
		con = DBConnection.getConnection();
	}
	
	public double getHighestThreshold(String accountID) {
		String query = "select max(threshold) from account_loan natural join loan where account_num = '" + accountID + "'";
		double threshold = 0;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				threshold = result.getDouble(1);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return threshold;
	}
	
	public String getLatestLoanID() {
		String loanID = null;
		String query = "select max(loan_id) from loan";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				loanID = result.getString(1);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (loanID == null) {
			loanID = "L000001";
		}
		return loanID;
	}
	
	public boolean createNewLoan(String accountNum, Loan newLoan) {
//		insert into loan values('L000001', '10000', '9', '2000');
//		insert into account_loan values('A000001', 'L000001', '10000', '0', '1-Apr-2014', '1-Apr-2014');
//		insert into transaction values('T000032', 'A000001', '10000', '1-Apr-2014', '4');
//		insert into loan_transaction values('T000032', 'L000001');
		// with trigger - loanTransaction
		
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
	    TransControl control = new TransControl();
	    String newTransID = Utility.getNewID(control.getLatestTransID());
	    
		String query1 = "insert into loan values('" + newLoan.getLoanID() + "', '" + newLoan.getTotalAmount() + 
							"', '" + newLoan.getInterestRate() + "', '" + newLoan.getThreshold() + "')";
		String query2 = "insert into account_loan values('" + accountNum + "', '" + newLoan.getLoanID() + 
							"', '" + newLoan.getTotalAmount() + "', '0', TO_DATE('" + sqlDate + "','YYYY-MM-DD')" +
							", TO_DATE('" + sqlDate + "','YYYY-MM-DD'))";
		String query3 = "insert into transaction values('" + newTransID + "', '" + accountNum + 
							"', '" + newLoan.getTotalAmount() + "', TO_DATE('" + sqlDate + "','YYYY-MM-DD'), '4')";
		String query4 = "insert into loan_transaction values('" + newTransID + "', '" + newLoan.getLoanID() + "')";
		
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
	
	public boolean makePayment(String accountNum, String loanID, double amount) {
//		insert into transaction values('T000034', 'A000001', '500', '16-Apr-2014', '5');
//		insert into loan_transaction values('T000034', 'L000001');
		boolean flag = false;
		
		if (updateLoan(accountNum, loanID)) {
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    
		    TransControl control = new TransControl();
		    String newTransID = Utility.getNewID(control.getLatestTransID());
	
			String query1 = "insert into transaction values('" + newTransID + "', '" + accountNum + 
								"', '" + amount + "', TO_DATE('" + sqlDate + "','YYYY-MM-DD'), '5')";
			String query2 = "insert into loan_transaction values('" + newTransID + "', '" + loanID + "')";
			
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
		}
		return flag;
	}
	
	public double getInterestRate(String loanID) {
		String query = "select interest_rate from loan where loan_id = '" + loanID + "'";
		double rate = 0;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				rate = result.getDouble("interest_rate");
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rate;
	}
	
	public java.sql.Date getStartDate(String accountNum, String loanID) {
		String query = "select start_date from account_loan where account_num = '" + 
						accountNum + "' and loan_id = '" + loanID + "'";
		java.sql.Date date = null;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				date = result.getDate("start_date");
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	public java.sql.Date getUpdateDate(String accountNum, String loanID) {
		String query = "select update_date from account_loan where account_num = '" + 
						accountNum + "' and loan_id = '" + loanID + "'";
		java.sql.Date date = null;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				date = result.getDate("update_date");
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	public boolean updateLoan(String accountNum, String loanID) {
//update account_loan set due_amount = due_amount * exp((9/100) * (10/365)), update_date = '16-APR-14' where account_num = 'A000001' and loan_id = 'L000001'
		double rate = getInterestRate(loanID) / 100;
		
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date today = new java.sql.Date(utilDate.getTime()); 
	    java.sql.Date oldDay = getUpdateDate(accountNum, loanID);
	  
	 	long days = (today.getTime() - oldDay.getTime()) / (1000 * 60 * 60 * 24);
	 	double exp = rate * ((double)days / 365);
	 	
		String query = "update account_loan set due_amount = ROUND(due_amount * exp(" + exp + "), 2), " +
						"update_date = TO_DATE('" + today + "','YYYY-MM-DD') where account_num = '" + accountNum + "' and loan_id = '" + loanID + "'";
//		System.out.println(query);
		
		boolean flag = false;
		Statement s;
		try {
			con.setAutoCommit(false);
			s = con.createStatement();
			
			int result;
			result = s.executeUpdate(query);
			
			if (result > 0) {
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
	
	public boolean updateLoan(String accountNum) {
		String query = "select loan_id from account_loan where account_num = '" + accountNum + "' and due_amount > 0";
		
		boolean flag = true;
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			result = s.executeQuery(query);
	
			while (result.next()) {
				String loanID = result.getString("loan_id");
				if (!updateLoan(accountNum, loanID)) {
					flag = false;
					break;
				}
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}
	
	public ArrayList<AccountLoan> getValidLoansOfAccount(String accountNum) {
		String query = "select * from account_loan where account_num = '" + accountNum + "' and due_amount > 0";
		ArrayList<AccountLoan> recordList = new ArrayList<AccountLoan>();
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	
			while (result.next()) {
				String loanID = result.getString("loan_id");
				double dueAmount = result.getDouble("due_amount");
				double paidAmount = result.getDouble("paid_amount");
				
				String startDate = df.format(result.getDate("start_date"));
				String endDate = df.format(result.getDate("update_date"));
				AccountLoan accountLoan = new AccountLoan(loanID, dueAmount, paidAmount, startDate, endDate);
				recordList.add(accountLoan);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recordList;
	}
	
	public static void main(String args[]) {
		LoanControl control = new LoanControl(); 
//		String newLoanID = Utility.getNewID(control.getLatestLoanID()); 
//		Loan loan = new Loan(newLoanID, 5000, 9, 1500);
//		System.out.println(control.createNewLoan("A000005", loan));
		
		System.out.println(control.updateLoan("A000001"));
	}
}
