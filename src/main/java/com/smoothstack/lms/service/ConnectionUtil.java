/**
 * 
 */
package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ppradhan
 *
 */
public class ConnectionUtil {
	
	public final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public final String URL;
	public final String USER_NAME;
	public final String PASS;

	ConnectionUtil() {
		AccessCredentials creds = new AccessCredentials();
		URL = "jdbc:mysql://" + creds.getDbHost() + ":" + creds.getDbPort() + "/library?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
		USER_NAME = creds.getDbUser();
		PASS = creds.getDbPass();
	}
	public Connection getConnection(){
		new AccessCredentials().toString();
		Connection conn = null;
		try {
			Class.forName(DRIVER_NAME); //WT* is this ?
			conn = DriverManager.getConnection(URL, USER_NAME, PASS);
			conn.setAutoCommit(Boolean.FALSE);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
		
	}
}
