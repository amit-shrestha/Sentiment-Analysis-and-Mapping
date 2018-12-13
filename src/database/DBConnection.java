package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getDBCon(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter_database","root","");
				return con;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;
		
	}
}
