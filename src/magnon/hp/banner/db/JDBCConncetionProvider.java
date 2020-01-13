package magnon.hp.banner.db;

import java.sql.*;  // Using 'Connection', 'Statement' and 'ResultSet' classes in java.sql package

public class JDBCConncetionProvider {   // Save as "JdbcSelectTest.java"
	public static void main(String[] args) {

		JDBCConncetionProvider js = new JDBCConncetionProvider();
		js.connect();// Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
	}

	private String userName = "bannerdbuser";
	private String password = "bannerdbuser";
	private String url = "jdbc:mysql://127.0.0.1:3306/banner?autoReconnect=true&useSSL=false";

	public Connection connect()
	{
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			conn  = DriverManager.getConnection(url,userName,password);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}