package yil712.element;

public class Loan {
	protected String loan_id;
	protected double total_amount;
	protected double rate;
	protected double threshold;
	
	public Loan(){
		this.loan_id = "";
		this.total_amount = 0;
		this.rate = 0;
		this.threshold = 0;
	}
	
	public Loan(String loan_id, double total_amount, double rate, double threshold) {
		this.loan_id = loan_id;
		this.total_amount = total_amount;
		this.rate = rate;
		this.threshold = threshold;
	}
	
	public String getLoanID() {
		return loan_id;
	}
	
	public double getTotalAmount() {
		return total_amount;
	}
	
	public double getInterestRate() {
		return rate;
	}
	
	public double getThreshold() {
		return threshold;
	}
	
	public void setLoanID(String loan_id) {
		this.loan_id = loan_id;
	}
	
	public void setTotalAmount(double total_amount) {
		this.total_amount = total_amount;
	}
	
	public void setInterestRate(double rate) {
		this.rate = rate;
	}
	
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
}
