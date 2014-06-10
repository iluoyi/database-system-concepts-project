package yil712.element;

public class AccountLoan {
	protected String loan_id;
	protected double due_amount;
	protected double paid_amount;
	protected String startDate;
	protected String updateDate;
	
	public AccountLoan(){
		this.loan_id = "";
		this.due_amount = 0;
		this.paid_amount = 0;
		this.startDate = "";
		this.updateDate = "";
	}
	
	public AccountLoan(String loan_id, double due_amount, double paid_amount, String startDate, String updateDate) {
		this.loan_id = loan_id;
		this.due_amount = due_amount;
		this.paid_amount = paid_amount;
		this.startDate = startDate;
		this.updateDate = updateDate;
	}
	
	public String getLoanID() {
		return loan_id;
	}
	
	public double getDueAmount() {
		return due_amount;
	}
	
	public double getPaidAmount() {
		return paid_amount;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setLoanID(String loan_id) {
		this.loan_id = loan_id;
	}
	
	public void setDueAmount(double due_amount) {
		this.due_amount = due_amount;
	}
	
	public void setPaiedAmount(double paid_amount) {
		this.paid_amount = paid_amount;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
