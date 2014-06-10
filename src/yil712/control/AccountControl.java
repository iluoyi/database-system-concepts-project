package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yil712.element.Account;
import yil712.element.CashAccount;
import yil712.element.InvestAccount;
import yil712.element.RetireAccount;

public class AccountControl {
	private Connection con = null;
	
	public AccountControl() {
		con = DBConnection.getConnection();
	}
	
	public boolean deleteAccount(String accountID) {
		String query = "update account set valid_mark = '0' where account_num = '" + accountID + "'";
		
		boolean flag = false;
		
		Statement s;
		try {
			s = con.createStatement();
			
			int result;
			result = s.executeUpdate(query);
			
			if (result > 0) {
				flag = true;
			}			
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public double getBalance(String accountID) {
		String query1 = "select balance from cash_account where account_num = '" + accountID + "'";
		String query2 = "select balance from invest_account where account_num = '" + accountID + "'";
		String query3 = "select balance from retire_account where account_num = '" + accountID + "'";
		double balance = 0;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query1);		
			if (result.next()) {
				balance = result.getDouble("BALANCE");
			} else {
				result = s.executeQuery(query2);
				if (result.next()) {
					balance = result.getDouble("BALANCE");
				} else {
					result = s.executeQuery(query3);
					if (result.next()) {
						balance = result.getDouble("BALANCE");
					}
				}
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}
	
	public Account getOneAccount(String customerID, String accountID) {
		String query = "select * from account where account_num = '" + accountID + "' and customer_id = '" + customerID + "'";
		Account account = null;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			while (result.next()) {
				String DOO = result.getString("DATE_OF_OPEN");
				String location = result.getString("BRANCH_LOC");
				double balance = getBalance(accountID);
				int type = getAccountType(accountID);
				
				switch (type) {
					case 1:	 account = new CashAccount(accountID, customerID, DOO, location, balance);
							 break;
					case 2:  account = new InvestAccount(accountID, customerID, DOO, location, balance);
							 break;
					case 3: account = new RetireAccount(accountID, customerID, DOO, location, balance);
							 break;
				}
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account; // might be null
	}
	
	public ArrayList<String> getValidAccountIDList(String customerID) {
		ArrayList<String> accountList = new ArrayList<String>();
		String query = "select account_num from account";
		if (customerID != null) {
			query += " where customer_id = '" + customerID + "' and valid_mark = '1'";
		}
		
		Statement s;
		try {
			s = con.createStatement();
			ResultSet result;
			result = s.executeQuery(query);
			
			while (result.next()) {
				String accountID = result.getString("ACCOUNT_NUM");
				accountList.add(accountID);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}
	
	public int getAccountType(String accountID) {
		String query1 = "select * from cash_account where account_num = '" + accountID + "'";
		String query2 = "select * from invest_account where account_num = '" + accountID + "'";
		String query3 = "select * from retire_account where account_num = '" + accountID + "'";
		int accountType = 0;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query1);		
			if (result.next()) {
				accountType = 1; // cash
			} else {
				result = s.executeQuery(query2);
				if (result.next()) {
					accountType = 2; // invest
				} else {
					result = s.executeQuery(query3);
					if (result.next()) {
						accountType = 3; // retire
					}
				}
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountType;
	}
	
	public ArrayList<CashAccount> getValidCashAccounts(String customerID) {
		ArrayList<CashAccount> accountList = new ArrayList<CashAccount>();
		String query = "select * from cash_account natural join account where customer_id = '" + 
						customerID + "' and valid_mark = '1' order by account_num";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			while (result.next()) {
				String accountID = result.getString("ACCOUNT_NUM");
				String DOO = result.getString("DATE_OF_OPEN");
				String location = result.getString("BRANCH_LOC");
				double balance = result.getDouble("BALANCE");
				CashAccount tempAccount = new CashAccount(accountID, customerID, DOO, location, balance);
				accountList.add(tempAccount);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}
	
	public ArrayList<InvestAccount> getValidInvestAccounts(String customerID) {
		ArrayList<InvestAccount> accountList = new ArrayList<InvestAccount>();
		String query = "select * from invest_account natural join account where customer_id = '" + 
						customerID + "' and valid_mark = '1' order by account_num";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			while (result.next()) {
				String accountID = result.getString("ACCOUNT_NUM");
				String DOO = result.getString("DATE_OF_OPEN");
				String location = result.getString("BRANCH_LOC");
				double balance = result.getDouble("BALANCE");
				InvestAccount tempAccount = new InvestAccount(accountID, customerID, DOO, location, balance);
				accountList.add(tempAccount);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}
	
	public ArrayList<RetireAccount> getValidRetireAccounts(String customerID) {
		ArrayList<RetireAccount> accountList = new ArrayList<RetireAccount>();
		String query = "select * from retire_account natural join account where customer_id = '" + 
						customerID + "' and valid_mark = '1' order by account_num";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			while (result.next()) {
				String accountID = result.getString("ACCOUNT_NUM");
				String DOO = result.getString("DATE_OF_OPEN");
				String location = result.getString("BRANCH_LOC");
				double balance = result.getDouble("BALANCE");
				RetireAccount tempAccount = new RetireAccount(accountID, customerID, DOO, location, balance);
				accountList.add(tempAccount);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}
	
	public String getLatestAccountNum() {
		String accountNum = null;
		String query = "select max(account_num) from account";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				accountNum = result.getString(1);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (accountNum == null) {
			accountNum = "A000001";
		}
		return accountNum;
	}
	
	public boolean createCashAccount(CashAccount cashAccount) {
		String query1 = "insert into account ";
		query1 += "values ('" + cashAccount.getAccountID() + "', '" + cashAccount.getCustomerID() + 
					"', '" + cashAccount.getDOO() + "', '" + cashAccount.getBranchLoc() + "', '1')";
		String query2 = "insert into cash_account ";
		query2 += "values ('" + cashAccount.getAccountID() + "', '" + cashAccount.getBalance() + "')";
				
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
	
	public boolean createInvestAccount(InvestAccount investAccount) {
		String query1 = "insert into account ";
		query1 += "values ('" + investAccount.getAccountID() + "', '" + investAccount.getCustomerID() + 
					"', '" + investAccount.getDOO() + "', '" + investAccount.getBranchLoc() + "', '1')";
		String query2 = "insert into invest_account ";
		query2 += "values ('" + investAccount.getAccountID() + "', '" + investAccount.getBalance() + "')";
		
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
	
	public boolean createRetireAccount(RetireAccount retireAccount) {
		String query1 = "insert into account ";
		query1 += "values ('" + retireAccount.getAccountID() + "', '" + retireAccount.getCustomerID() + 
					"', '" + retireAccount.getDOO() + "', '" + retireAccount.getBranchLoc() + "', '1')";
		String query2 = "insert into retire_account ";
		query2 += "values ('" + retireAccount.getAccountID() + "', '" + retireAccount.getBalance() + "')";
		
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
		AccountControl accountControl = new AccountControl(); 
		
//		CashAccount account = new CashAccount("A000001", "C000001", "03/18/1989", "431 Montclair Ave", 1000.85);
//		System.out.println(accountControl.createCashAccount(account));
		
//		InvestAccount account = new InvestAccount("A000002", "C000001", "03/18/1989", "431 Montclair Ave", 2000.85);
//		System.out.println(accountControl.createInvestAccount(account));
		
//		RetireAccount account = new RetireAccount("A000003", "C000001", "03/18/1989", "431 Montclair Ave", 2000.85);
//		System.out.println(accountControl.createRetireAccount(account));
		
//		System.out.println(accountControl.getLatestAccountNum());
		
		ArrayList<CashAccount> accountList = accountControl.getValidCashAccounts("C000001");
		if (accountList != null && accountList.size() > 0) {
			for (CashAccount account : accountList) {
				System.out.println(account.getAccountID());
			}
		}
		
//		System.out.println(accountControl.getBalance("A000002"));
		
//		System.out.println(accountControl.getOneAccount("C000001", "A000002").getBranchLoc());
	}
}
