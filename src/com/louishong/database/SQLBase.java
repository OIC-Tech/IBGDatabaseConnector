package com.louishong.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Used to fetch data from the database.
 * 
 * @author Louis Hong
 * @version 1.0
 */
public class SQLBase {

	/**
	 * The driver used to connect
	 */
	public String sDriver;
	/**
	 * The location of the database
	 */
	public String sUrl;
	/**
	 * The connector for the database.
	 */
	public Connection con;

	/**
	 * Initializes the database wrapper.
	 * 
	 * @param stringDriver
	 * @param stringUrl
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public SQLBase(String stringDriver, String stringUrl)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		sDriver = stringDriver;
		sUrl = stringUrl;

		// Register Driver #1
		setDriver(sDriver);

		// Create Connection
		createConnection();
	}

	/**
	 * Sets up the database driver.
	 * 
	 * @param driver
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public void setDriver(String driver) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Register Driver #1
		Driver d = (Driver) Class.forName(driver).newInstance();
		DriverManager.registerDriver(d);

	}

	/**
	 * Activates the connection with the database.
	 * 
	 * @throws SQLException
	 */
	public void createConnection() throws SQLException {
		try {
			con = DriverManager.getConnection(sUrl);
		} catch (SQLException e) {
			throw (e);
		}
	}

	/**
	 * Closes the connection with the database.
	 * 
	 * @throws SQLException
	 */
	public void cutConnection() throws SQLException {
		con.close();
	}

	/**
	 * Fetches the query results from the statement.
	 * 
	 * @param sql
	 * @return ResultSet containing the query results.
	 * @throws SQLException
	 */
	public ResultSet fetchQuery(String sql) throws SQLException {

		// Execute SQL Query using Statement object
		Statement stmt;
		stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		return result;

	}

	/**
	 * Fetches the query results from the prepared statement. prepared
	 * statements are used to counter SQL Injections
	 * 
	 * @param sql
	 * @param params
	 * @return ResultSet containing the results of the executed statement.
	 * @throws SQLException
	 */
	public ResultSet fetchQueryPrepared(String sql, String... params)
			throws SQLException {

		// Execute SQL Query using Statement object
		PreparedStatement stmt = con.prepareStatement(sql);

		int paramIndex = 1;
		for (String param : params) {
			stmt.setString(paramIndex++, param);
		}
		return stmt.executeQuery();

	}

	/**
	 * Executes a SQL statement with no results.
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void executeQuery(String sql) throws SQLException {

		// Execute SQL Query using Statement object
		Statement stmt;
		stmt = con.createStatement();
		stmt.executeUpdate(sql);

	}

	/**
	 * Executes a prepared statement that has no results. prepared statements
	 * are used to counter SQL Injections.
	 * 
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	public void executeQueryPrepared(String sql, String... params)
			throws SQLException {

		// Execute SQL Query using Statement object
		PreparedStatement stmt = con.prepareStatement(sql);

		int paramIndex = 1;
		for (String param : params) {
			stmt.setString(paramIndex++, param);
		}
		stmt.executeUpdate();

	}
	
	
}
