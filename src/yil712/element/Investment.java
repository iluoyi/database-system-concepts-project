package yil712.element;

import java.sql.Timestamp;

public class Investment {
	protected String symbol;
	protected Timestamp time;
	protected double price;
	
	public Investment(){
		this.symbol = "";
		this.time = null;
		this.price = 0;
	}
	
	public Investment(String symbol, Timestamp time, double price) {
		this.symbol = symbol;
		this.time = time;
		this.price = price;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public Timestamp getTimestamp() {
		return time;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
}
