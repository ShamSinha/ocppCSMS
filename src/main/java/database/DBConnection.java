/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Shubham
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
	private static String dbhost = "jdbc:mysql://localhost:3306/";
	private static String username = "root";
	private static String password = "Shubh@m19";
	private static Connection conn;
	
	@SuppressWarnings("finally")
	public static Connection createNewDBconnection(String s) {
		try  {	
			conn = DriverManager.getConnection(
					dbhost + s, username, password);	
		} catch (SQLException e) {
			System.out.println("Cannot create database connection");
			e.printStackTrace();
		} finally {
			return conn;	
		}		
	}
}
