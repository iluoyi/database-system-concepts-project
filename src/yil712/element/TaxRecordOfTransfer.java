package yil712.element;

public class TaxRecordOfTransfer {
	String toAccount;
	String date;
	double amount;
	
	public TaxRecordOfTransfer() {
		this.toAccount = "";
		this.date = "";
		this.amount = 0;
	}
	
	public TaxRecordOfTransfer (String toAccount, String date, double total) {
		this.toAccount = toAccount;
		this.date = date;
		this.amount = total;
	}
	
	public String getToAccount(){
		return toAccount;
	}
	
	public String getDate(){
		return date;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
