package yil712.element;

public class CashAccount extends Account {
	private double balance;
	
	public CashAccount() {
		super();
		this.balance = 0;
	}
	
	public CashAccount(String account_id, String customer_id, String date_of_open, String branch_location, double balance) {
		super(account_id, customer_id, date_of_open, branch_location);
		this.balance = balance;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
