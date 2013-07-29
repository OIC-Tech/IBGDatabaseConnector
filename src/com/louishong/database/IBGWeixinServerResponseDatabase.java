package com.louishong.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used for fetching data through the response database.
 * 
 * @author Louis Hong
 * @version 1.0
 */

public class IBGWeixinServerResponseDatabase {

	// SQL base declare
	public static SQLiteBase sqlBase;

	/**
	 * Initializes the SQLiteBase Connector to the database.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 * 
	 * @see SQLiteBase
	 */
	public IBGWeixinServerResponseDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		// JDBC Driver
		String sDriver = "org.sqlite.JDBC";
		// Database URL location
		final String sUrl = DatabaseLocation.getResponsesURL();
		sqlBase = new SQLiteBase(sDriver, sUrl);
	}

	/**
	 * Initializes the SQLiteBase Connector to the database with the costume
	 * driver and URL.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 * @see SQLiteBase
	 */
	public IBGWeixinServerResponseDatabase(String driver, String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		sqlBase = new SQLiteBase(driver, url);
	}

	/* ==========The Readers=========== */

	/**
	 * Searches the database through the SQLBase connector object for a random
	 * String response.
	 * 
	 * @param input
	 * @return ResultSet with a single row. Containing a random response result
	 *         from the database.
	 * @throws SQLException
	 */
	public ResultSet searchResponse(String input) throws SQLException {
		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM (SELECT * FROM Responses WHERE Inputs=?) ORDER BY RANDOM() LIMIT 1", input);
		return results;
	}

	/**
	 * Getting the total response for a input in the database.
	 * 
	 * @param input
	 * @return int of the responses available.
	 * @throws SQLException
	 */
	public int length(String input) throws SQLException {
		return Integer.parseInt(sqlBase.fetchQueryPrepared("SELECT COUNT(?) AS DataAmount FROM Responses", input).getString("DataAmount"));
	}

	/**
	 * Cuts the connection with the database.
	 * 
	 * @throws SQLException
	 */

	/* ==========The Writers=========== */

	public void addResponse(String input, String response) throws SQLException {
		sqlBase.executeQueryPrepared("INSERT INTO Responses (Inputs, Responses, Dynamic) VALUES (?, ?, ?)", input, response, "0");
	}

	/* ==========Utilities=========== */
	public boolean isUnique(String input, String response, boolean dynamic) throws SQLException {
		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM Responses WHERE Inputs=? AND Responses=? AND Dynamic=?", input, response, dynamic ? "1" : "0");
		return !results.next();
	}

	public void cutConnection() throws SQLException {
		sqlBase.cutConnection();
	}

}
