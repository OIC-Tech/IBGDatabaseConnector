/**
 * Purpose: Used for fetching data through the response database.
 *
 * @author Louis Hong
 * @version 1.0
 */

package com.louishong.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IBGWeixinServerResponseDatabase {

	// JDBC Driver
	public static String sDriver = "org.sqlite.JDBC";
	// Database URL location
	final public static String sUrl = DataBaseLocation.responsesURL;
	// SQL base declare
	public static SQLiteBase sqlBase;

	/**
	 * Initializes the SQLiteBase Connector to the database.
	 * 
	 * @see SQLiteBase
	 */
	public IBGWeixinServerResponseDatabase() {
		sqlBase = new SQLiteBase(sDriver, sUrl);
	}

	/**
	 * Initializes the SQLiteBase Connector to the database with the costume
	 * driver and URL.
	 * 
	 * @see SQLiteBase
	 */
	public IBGWeixinServerResponseDatabase(String driver, String url) {
		sqlBase = new SQLiteBase(driver, url);
	}

	/* ==========The Readers=========== */

	/**
	 * Searches the database through the SQLBase connector object for a random
	 * String response.
	 * 
	 * @param input
	 * @return <code>ResultSet</code> with a single row. Containing a random
	 *         response result from the database.
	 * @throws SQLException
	 */
	public ResultSet searchResponse(String input) throws SQLException {
		ResultSet results = sqlBase.executeQueryPrepared("SELECT * FROM (SELECT * FROM Responses WHERE Inputs=?) ORDER BY RANDOM() LIMIT 1", input);
		return results;
	}

	/**
	 * Getting the total response for a input in the database.
	 * 
	 * @param input
	 * @return <code>int</code> of the responses available.
	 * @throws SQLException
	 */
	public int length(String input) throws SQLException {
		return Integer.parseInt(sqlBase.executeQueryPrepared("SELECT COUNT(?) AS DataAmount FROM Responses", input).getString("DataAmount"));
	}

	/**
	 * Cuts the connection with the database
	 * 
	 * @throws SQLException
	 */
	public void cutConnection() throws SQLException {
		sqlBase.cutConnection();
	}

	/* ==========The Writers=========== */

}
