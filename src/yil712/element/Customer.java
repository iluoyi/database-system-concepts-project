package yil712.element;

/**
 * This is an element class of a customer
 */
public class Customer {
	String name;
	String customer_id;
	String phone_num;
	String address;
	String broker_id;
	
	public Customer(){
		this.name = "";
		this.customer_id = "";
		this.phone_num = "";
		this.address = "";
		this.broker_id = "";
	}
	
	/**
	 * String name, String customer_id, String broker_id, String phone_num, String address
	 * 
	 */
	public Customer(String name, String customer_id, String broker_id, String phone_num, String address) {
		this.name = name;
		this.customer_id = customer_id;
		this.phone_num = phone_num;
		this.address = address;
		this.broker_id = broker_id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCustomerID(){
		return customer_id;
	}
	
	public String getPhoneNum(){
		return phone_num;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getBrokerID() {
		return broker_id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setCustomerID(String customer_id){
		this.customer_id = customer_id;
	}
	
	public void setPhoneNo(String phone_num){
		this.phone_num = phone_num;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setBrokerID(String broker_id) {
		this.broker_id = broker_id;
	}
}
