package yil712.element;

import java.sql.Date;

public class Transaction {
	Date date;
	int type;
	String receiver;
	double amount;
	
	public Transaction(){
		this.date = null;
		this.type = 0;
		this.receiver = "";
		this.amount = 0;
	}
	
	public Transaction(Date date, int type, String receiver, double amount) {
		this.date = date;
		this.type = type;
		this.receiver = receiver;
		this.amount = amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getReceiver() {
		switch (type) {
			case 3: return "Account: " + receiver;
			case 4: return "Loan: " + receiver;
			case 5: return "Loan: " + receiver;
			case 6: return "Symbol: " + receiver;
			case 7: return "Symbol: " + receiver;
			case 8: return "Symbol: " + receiver;
			case 9: return "Symbol: " + receiver;
			default: return receiver;
		}
	}
	
	public String getType() {
		switch (type) {
			case 1: return "Deposit";
			case 2: return "Withdrawal";
			case 3: return "Transfer";
			case 4: return "OpenLoan";
			case 5: return "PayLoan";
			case 6: return "Buy stocks/funds";
			case 7: return "Sell stocks/funds";
			case 8: return "Buy stocks/funds";
			case 9: return "Sell stocks/funds";
			default: return "Unknown";
		}
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setReceier(String receiver) {
		this.receiver = receiver;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
