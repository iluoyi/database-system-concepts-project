package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yil712.element.Broker;

public class BrokerControl {
	private Connection con = null;
	
	public BrokerControl() {
		con = DBConnection.getConnection();
	}
	
	public String getLatestBrokerID() {
		String brokerID = null;
		String query = "select max(broker_id) from broker";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			if (result.next()) {
				brokerID = result.getString(1);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (brokerID == null) {
			brokerID = "B000001";
		}
		return brokerID;
	}
	
	public boolean createNewBroker(Broker broker, String pwd) {
		String query = "insert into broker values('" + broker.getBrokerID() + "', '" + broker.getName() + "', '" + 
						broker.getPhoneNum() + "', '" + broker.getBranchLoc() + "', '" + pwd + "')";
 				
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
	
	public ArrayList<String> getAllBrokerIDs() {
		ArrayList<String> brokerIDs = new ArrayList<String>();
		String query = "select broker_id from broker";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			while (result.next()) {
				brokerIDs.add(result.getString("broker_id"));
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return brokerIDs;
	}
	
	public ArrayList<String> getCustomerIDsOf(String brokerID) {
		ArrayList<String> customerIDs = new ArrayList<String>();
		String query = "select customer_id from customer where broker_id = '" + brokerID + "'";
		
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			
			result = s.executeQuery(query);
			
			while (result.next()) {
				customerIDs.add(result.getString("customer_id"));
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customerIDs;
	}
}
