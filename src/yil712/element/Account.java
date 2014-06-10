package yil712.element;

public class Account {
	protected String account_id;
	protected String date_of_open;
	protected String branch_location;
	protected String customer_id;
	
	public Account(){
		this.account_id = "";
		this.date_of_open = "";
		this.branch_location = "";
		this.customer_id = "";
	}
	
	public Account(String account_id, String customer_id, String date_of_open, String branch_location) {
		this.account_id = account_id;
		this.date_of_open = date_of_open;
		this.branch_location = branch_location;
		this.customer_id = customer_id;
	}
	
	public String getAccountID() {
		return account_id;
	}
	
	public String getDOO() {
		return date_of_open;
	}
	
	public String getBranchLoc() { 
		return branch_location;
	}
	
	public String getCustomerID() {
		return customer_id;
	}
 	
	public void setAccountID(String account_id) {
		this.account_id = account_id;
	}
	
	public void setDOO(String date_of_open) {
		this.date_of_open = date_of_open;
	}
	
	public void setBranchLoc(String branch_location) {
		this.branch_location = branch_location;
	}
	
	public void setCustomerID(String customer_id) {
		this.customer_id = customer_id;
	}
}
