package yil712.element;

public class TaxRecordOfInvest {
	String symbol;
	int shortOrLong;
	double total;
	
	public TaxRecordOfInvest() {
		this.symbol = "";
		this.shortOrLong = 0;
		this.total = 0;
	}
	
	public TaxRecordOfInvest (String symbol, int shortOrLong, double total) {
		this.symbol = symbol;
		this.shortOrLong = shortOrLong;
		this.total = total;
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public int getShortOrLong(){
		return shortOrLong;
	}
	
	public double getTotal(){
		return total;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public void setShortOrLong(int shortOrLong) {
		this.shortOrLong = shortOrLong;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
}
