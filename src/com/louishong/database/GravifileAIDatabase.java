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

public class GravifileAIDatabase {

	// SQL base declare
	public static SQLBase sqlBase;
	private static String sUrl = null;
	static {
		try {
			sUrl = DatabaseURL.getConversationURL();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initializes the SQLiteBase Connector to the database.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 * 
	 * @see SQLBase
	 */
	public GravifileAIDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		// JDBC Driver
		String sDriver = "com.mysql.jdbc.Driver";
		sqlBase = new SQLBase(sDriver, sUrl);
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
	 * @see SQLBase
	 */
	public GravifileAIDatabase(String driver, String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		sqlBase = new SQLBase(driver, url);
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
		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM (SELECT * FROM Conversation WHERE Inputs=?) AS Results ORDER BY RAND() LIMIT 1", input);
		return results;
	}

	/**
	 * Getting the total response for a input in the database.
	 * 
	 * @param input
	 * @return int of the Conversation available.
	 * @throws SQLException
	 */
	public int length(String input) throws SQLException {
		ResultSet results = sqlBase.fetchQueryPrepared("SELECT COUNT(Inputs) AS DataAmount FROM Conversation WHERE Inputs=?", input);
		results.next();
		return results.getInt("DataAmount");
	}

	/**
	 * Cuts the connection with the database.
	 * 
	 * @throws SQLException
	 */

	/* ==========The Writers=========== */

	public void addConversation(String input, String response, boolean dynamic) throws SQLException {

		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM Conversation WHERE Inputs=? AND Responses=? AND Dynamic=b?", input, response, dynamic ? "1" : "0");
		if (results.next()) {
			if (results.getBoolean("readonly")) {
				throw new SQLException("This conversation is read only");
			}
		}
		sqlBase.executeQueryPrepared("INSERT INTO Conversation (Inputs, Responses, Dynamic) VALUES (?, ?, b?)", input, response, dynamic ? "1" : "0");
	}

	public void deleteConversation(String input, String response, boolean dynamic) throws SQLException {

		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM Conversation WHERE Inputs=? AND Responses=? AND Dynamic=b?", input, response, dynamic ? "1" : "0");
		if (results.next()) {
			if (results.getBoolean("readonly")) {
				throw new SQLException("This conversation is read only");
			}
		}
		sqlBase.executeQueryPrepared("DELETE FROM Conversation WHERE Inputs=? AND Responses=? AND Dynamic=b?", input, response, dynamic ? "1" : "0");
	}

	public void addConversation(String input, String response, boolean dynamic, boolean readonly, boolean force) throws SQLException {
		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM Conversation WHERE Inputs=? AND Responses=? AND Dynamic=b?", input, response, dynamic ? "1" : "0");
		if (results.next() && !force) {
			if (results.getBoolean("readonly")) {
				throw new SQLException("This conversation is read only");
			}
		}
		sqlBase.executeQueryPrepared("INSERT INTO Conversation (Inputs, Responses, Dynamic, readonly) VALUES (?, ?, b?, b?)", input, response, dynamic ? "1" : "0", readonly ? "1" : "0");
	}

	public void deleteConversation(String input, String response, boolean dynamic, boolean force) throws SQLException {
		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM Conversation WHERE Inputs=? AND Responses=? AND Dynamic=b?", input, response, dynamic ? "1" : "0");
		if (results.next() && !force) {
			if (results.getBoolean("readonly")) {
				throw new SQLException("This conversation is read only");
			}
		}
		sqlBase.executeQueryPrepared("DELETE FROM Conversation WHERE Inputs=? AND Responses=? AND Dynamic=b?", input, response, dynamic ? "1" : "0");
	}

	/* ==========Utilities=========== */
	public boolean isUnique(String input, String response, boolean dynamic) throws SQLException {
		ResultSet results = sqlBase.fetchQueryPrepared("SELECT * FROM Conversation WHERE Inputs=? AND Responses=? AND Dynamic=?", input, response, dynamic ? "1" : "0");
		return !results.next();
	}

	public void cutConnection() throws SQLException {
		sqlBase.cutConnection();
	}

}
