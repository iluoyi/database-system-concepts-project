package yil712.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection con = null;
	
	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(
						"jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241","youruserid",
						"yourpassword");
			} catch (SQLException e) {
				System.out.println("Error: Invalid SQL command.");
			} catch (ClassNotFoundException e) {
				System.out.println("Error: The class 'oracle.jdbc.driver.OracleDriver' cannot be located.");
			}
		}
		return con;
	}
	
	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
