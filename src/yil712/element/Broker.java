package yil712.element;

public class Broker {
	String name;
	String broker_id;
	String phone_num;
	String branch_loc;
	
	public Broker(){
		this.name = "";
		this.broker_id = "";
		this.phone_num = "";
		this.branch_loc = "";
	}
	
	public Broker(String name, String broker_id, String phone_num, String branch_loc) {
		this.name = name;
		this.broker_id = broker_id;
		this.phone_num = phone_num;
		this.branch_loc = branch_loc;
	}
	
	public String getName(){
		return name;
	}
	
	public String getBrokerID(){
		return broker_id;
	}
	
	public String getPhoneNum(){
		return phone_num;
	}
	
	public String getBranchLoc() {
		return branch_loc;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setBrokerID(String broker_id){
		this.broker_id = broker_id;
	}
	
	public void setPhoneNo(String phone_num){
		this.phone_num = phone_num;
	}
	
	public void setBranchLoc(String address) {
		this.branch_loc = address;
	}
}
