package com.revature.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {
	
	private static Connection conn;

	public static Connection getConnection() {

		if (conn == null) {
			try {
				conn = DriverManager.getConnection(System.getenv("url"), System.getenv("user"),
						System.getenv("password"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	// Properties File Implementation
	public static Connection getConnectionViaProperties() {

		if (conn == null) {
			InputStream stream = null;
			Properties props = new Properties(); 

			try {
				stream = ConnectionFactory.class.getResourceAsStream("/credentials.properties");
				props.load(stream);
				conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"),
						props.getProperty("password"));
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return conn;
	}

}
