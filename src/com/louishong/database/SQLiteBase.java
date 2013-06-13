package com.louishong.database;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteBase {
	public static String sDriver;
	public static String sUrl;
	public static Connection con;

	public void setDriver(String driver) {
		// Register Driver #1
		try {
			Driver d = (Driver) Class.forName(driver).newInstance();
			DriverManager.registerDriver(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createConnection() {
		try {
			con = DriverManager.getConnection(sUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return;
		}

	}

	public void cutConnection() throws SQLException {
		con.close();
	}

	public ResultSet executeQuery(String sql) throws SQLException {

		// Execute SQL Query using Statement object
		Statement stmt;
		stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		return result;

	}

	public ResultSet executeQueryPrepared(String sql, String... params) throws SQLException {

		// Execute SQL Query using Statement object
		PreparedStatement stmt = con.prepareStatement(sql);

		int paramIndex = 1;
		for (String param : params) {
			stmt.setString(paramIndex++, param);
		}
		ResultSet result = stmt.executeQuery();
		return result;
	}

	public SQLiteBase(String stringDriver, String stringUrl) {

		sDriver = stringDriver;
		sUrl = stringUrl;

		// Register Driver #1
		setDriver(sDriver);

		// Create Connection
		createConnection();

	}

}
