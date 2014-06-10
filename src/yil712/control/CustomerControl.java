package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import yil712.element.Customer;

public class CustomerControl {
	private Connection con = null;
	
	public CustomerControl() {
		con = DBConnection.getConnection();
	}
	
	public boolean isExistedCustomer(String customerID) {
		String query = "select * from customer where customer_id = '" + customerID + "'";
		boolean flag = false;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				flag = true;
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public String getLatestCustomerID() {
		String customerID = null;
		String query = "select max(customer_id) from customer";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				customerID = result.getString(1);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (customerID == null) {
			customerID = "C000001";
		}
		return customerID;
	}
	
	public boolean createNewCustomer(Customer customer, String pwd) {
		//String name, String customer_id, String broker_id, String phone_num, String address
		String query = "insert into customer values('" + customer.getCustomerID() + "', '" + customer.getBrokerID() + "', '" + 
						customer.getName() + "', '" + customer.getPhoneNum() + "', '" + customer.getAddress() + "', '" + pwd + "')";
 				
		boolean flag = false;
		
		Statement s;
		try {
			s = con.createStatement();
			
			int result;
			result = s.executeUpdate(query);
			
			if (result > 0) {
				flag = true;
			}			
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}

}
