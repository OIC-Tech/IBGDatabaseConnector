package com.louishong.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper for the connection with Profile table.
 * 
 * @author Louis Hong
 * @version 1.1
 */

public class ProfileWrapper {
	/**
	 * The SQLite JDBC driver.
	 */
	public static String sDriver = "org.sqlite.JDBC";

	/**
	 * The URL of the Databases profiles.
	 */
	final public static String sUrl = DataBaseLocation.profileURL;

	/**
	 * The SQLBase used to the raw database.
	 */
	public static SQLiteBase sqlBase;

	/**
	 * Constructor that initializes SQLBase with default drivers and URL.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ProfileWrapper() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		sqlBase = new SQLiteBase(sDriver, sUrl);
	}

	/**
	 * Constructor that initializes SQLBase with costume drivers and URL.
	 * 
	 * @param driver
	 * @param url
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ProfileWrapper(String driver, String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		sqlBase = new SQLiteBase(driver, url);
	}

	/* ==========The Readers=========== */
	/**
	 * Used to get the entire Profiles table.
	 * 
	 * @return ResuldSet of the entire Profiles table.
	 */
	private ResultSet getProfiles() {
		try {
			return sqlBase.fetchQuery("SELECT * FROM Profiles");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Searches and returns the points a user has in the table.
	 * 
	 * @param name
	 * @return String of the points the user has.
	 */
	public String getUserPoint(String name) {
		ResultSet results = getProfiles();

		String resultName;
		try {

			while (results.next()) {
				resultName = results.getString("ChineseName");
				if (resultName.equals(name)) {
					return results.getString("UserPoints");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Searches in the table for the users email.
	 * 
	 * @param name
	 * @return String email of the user.
	 */
	public String getEmail(String name) {
		ResultSet results = getProfiles();

		String resultName;
		try {

			while (results.next()) {
				resultName = results.getString("ChineseName");
				if (resultName.equals(name)) {
					return results.getString("Email");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Searches for the existence of the user.
	 * 
	 * @param name
	 * @return <boolean>boolean true for exists false for doesn't exists.
	 */
	public Boolean hasUser(String name) {
		ResultSet results = getProfiles();

		String resultName;
		try {

			while (results.next()) {
				resultName = results.getString("ChineseName");
				if (resultName.equals(name)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Searches in the table for the users job.
	 * 
	 * @param name
	 * @return Stringthe users job.
	 */
	public String getUserJob(String name) {
		ResultSet results = getProfiles();

		String resultName;
		try {

			while (results.next()) {
				resultName = results.getString("ChineseName");
				if (resultName.equals(name)) {

					return results.getString("UserJob");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Searches in the table for the users phone number.
	 * 
	 * @param name
	 * @return String containing the users phone number.
	 */
	public String getUserPhone(String name) {
		ResultSet results = getProfiles();

		String resultName;
		try {

			while (results.next()) {
				resultName = results.getString("ChineseName");
				if (resultName.equals(name)) {

					return results.getString("UserPhone");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gives a 3 dimensional Map of the Profiles table.
	 * 
	 * @return Map<String, ArralyList<String>> with the names in the key and all
	 *         the information in the ArrayList.
	 */
	public Map<String, ArrayList<String>> getUserList() {
		ResultSet results = getProfiles();

		try {
			Map<String, ArrayList<String>> mapResults = new HashMap<String, ArrayList<String>>();
			ArrayList<String> data = new ArrayList<String>();
			while (results.next()) {
				data.clear();
				data.add(results.getString("UserJob"));
				data.add(results.getString("Email"));
				data.add(results.getString("UserPoints"));
				data.add(results.getString("UserPhone"));
				mapResults.put(results.getString("ChineseName"), new ArrayList<String>(data));
			}
			return mapResults;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/* ==========The Writers=========== */

}
