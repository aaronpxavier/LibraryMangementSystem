/**
 * 
 */
package com.smoothstack.lms.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * This is a demo class for JDBC Connection to Authors LMS Table
 * 
 * @author ppradhan
 *
 */
public class ReadAllAuthors {

	public static String driverName = "com.mysql.cj.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost:3306/library?useSSL=false";
	public static String userName = "root";
	public static String password = "root";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.err.println("Enter a Author Name to search: ");
		String authorName = scan.nextLine();
		String query = "SELECT * FROM tbl_author where authorName = '"+authorName+"'";
		try {
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(url, userName, password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				System.out.println("Author Name: "+ rs.getString("authorName"));
				System.out.println("Author ID: "+rs.getInt("authorId"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
