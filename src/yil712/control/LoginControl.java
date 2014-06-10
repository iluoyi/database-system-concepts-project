package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import yil712.element.Broker;
import yil712.element.Customer;

public class LoginControl {
	private Connection con = null;
	
	public LoginControl() {
		con = DBConnection.getConnection();
	}
	
	public boolean checkPassword(char role, String ID, String pwd) {
		String query = "select password from ";
		String idStr = null;
		if (role == 'C') {
			query += "customer ";
			idStr = "customer_id";
		} else {
			query += "broker ";
			idStr = "broker_id";
		}
		query += "where " + idStr + " = '" + ID + "'";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			result = s.executeQuery(query);
			
			while (result.next()) {
				String password = result.getString("password").trim();
				if (password.equals(pwd)) {
					return true;
				}
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Customer getCustomer(String customerID) {
		String query = "select broker_id, c_name, phone_num, address from customer where CUSTOMER_ID = '" + customerID + "'";
		Customer customer = null;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			result = s.executeQuery(query);
			
			while (result.next()) {
				String brokerID = result.getString("broker_id");
				String customerName = result.getString("c_name");
				String phoneNum = result.getString("phone_num");
				String address = result.getString("address");
				customer = new Customer(customerName, customerID, brokerID, phoneNum, address);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customer;
	}
	
	public Broker getBroker(String brokerID) {
		String query = "select b_name, phone_num, branch_loc from broker where BROKER_ID = '" + brokerID + "'";
		Broker broker = null;
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			result = s.executeQuery(query);
			
			while (result.next()) {
				String brokerName = result.getString("b_name");
				String phoneNum = result.getString("phone_num");
				String address = result.getString("branch_loc");
				broker = new Broker(brokerName, brokerID, phoneNum, address);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return broker;
	}

	public boolean registerCustomer(Customer newCustomer, String password) {
		String query = "insert into customer ";
		query += "values ('" + newCustomer.getCustomerID() + "', '" + newCustomer.getBrokerID() + "', '" + newCustomer.getName() +
				"', '" + newCustomer.getPhoneNum() + "', '" + newCustomer.getAddress() +
				"', '" + password + "')";
		//System.out.println(query);
		
		Statement s;
		try {
			s = con.createStatement();
			
			int result;
			result = s.executeUpdate(query);
			
			if (result >0) {
				return true;
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean registerBroker(Broker newBroker, String password) {
		String query = "insert into broker ";
		query += "values ('" + newBroker.getBrokerID() + "', '" + newBroker.getName() + 
					"', '" + newBroker.getPhoneNum() + "', '" + newBroker.getBranchLoc() +
					"', '" + password + "')";
		//System.out.println(query);
		
		Statement s;
		try {
			s = con.createStatement();
			
			int result;
			result = s.executeUpdate(query);
			
			if (result >0) {
				return true;
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String args[]) {
		LoginControl loginControl = new LoginControl(); 
		//System.out.println(loginControl.checkPassword('B', "B000001", "123456"));
		//Customer newCustomer = new Customer("Summer", "C000004", "484-664-0040", "03/18/1989", "431 Montclair Ave");
		
		//Broker newBroker = new Broker("Summer", "B000004", "484-664-0040", "431 Montclair Ave");
		//System.out.println(loginControl.registerBroker(newBroker, "111111"));
		
		System.out.println(loginControl.getBroker("B000001").getName());
	}
}
