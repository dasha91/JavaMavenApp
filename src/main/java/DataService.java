package com.cci.javademoapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataService {

	private String jdbcURLNoDB = "jdbc:mysql://localhost/";
	private String dbName = "DemoDb";
	private String tableName = "DemoTable";
	private String jdbcURL = "jdbc:mysql://localhost:3306/" + dbName;
	private String jdbcUsername = "root";
	private String jdbcPassword = "SuperS3cr3t";
	private Connection jdbcConnection;
	
	public DataService() {
		System.out.println("DataService Begin");
		 try {
			createDBAndTableIfDoesNotExist();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	 }
	 
	public void createDBAndTableIfDoesNotExist() throws ClassNotFoundException {
		System.out.println("createDBAndTableIfDoesNotExist Begin");
		// Create DB if it doesn't exist
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(jdbcURLNoDB, jdbcUsername, jdbcPassword);
		    Statement stmt = conn.createStatement();
		    stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
		    stmt.close();
		    conn.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		} 
		
		// Create table if it doesn't exist
		createTable();
	}
	
	public void createTable() {
		System.out.println("createTable Begin");
		connect(); 
		String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s(id INT PRIMARY KEY AUTO_INCREMENT, text VARCHAR(500) NOT NULL)", tableName);
		String addTableEntry = String.format("INSERT INTO %s(text) SELECT (\"Trial Text\") WHERE NOT EXISTS (SELECT * FROM %s)", tableName, tableName);
		try {
			PreparedStatement statement = jdbcConnection.prepareStatement(createTableQuery);
			statement.executeUpdate(); 
			statement = jdbcConnection.prepareStatement(addTableEntry);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
	}
	
	public void connect() {
		System.out.println("connect Begin");
		try {
			if (jdbcConnection == null || jdbcConnection.isClosed()) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		System.out.println("disconnect Begin");
		try {
			if (jdbcConnection != null && !jdbcConnection.isClosed()) {
				jdbcConnection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getCurrentText() {
		System.out.println("getCurrentText Begin");
		connect();
		String text = null;
		try {
			Statement statement = jdbcConnection.createStatement();
			ResultSet result = statement.executeQuery("Select * FROM " + tableName);
			while (result.next()) {
				text = result.getString("text");
			}
			result.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		return text;
	}
	
	public boolean updateText(String newText) {
		System.out.println("updateText Begin");
		connect(); 
		String sql = String.format("UPDATE %s SET text = ?", tableName);
		boolean rowUpdated = false;
		try {
			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setString(1, newText);
			rowUpdated = statement.executeUpdate() > 0;
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		return rowUpdated;
	}
}
