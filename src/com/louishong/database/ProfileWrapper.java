/** Purpose: A wrapper for the connection with Profile database
 *
 * @author Louis Hong
 * @version 1.1
 */

package com.louishong.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProfileWrapper {
	public static String sDriver = "org.sqlite.JDBC";

	final public static String sUrl = DataBaseLocation.profileURL;

	// Where the file is stored on The server
	// final public static String profileURL =
	// "jdbc:sqlite:C:\\OIC\\database\\Profiles.sqlite";

	public static SQLiteBase sqlBase;

	public ProfileWrapper() {
		sqlBase = new SQLiteBase(sDriver, sUrl);
	}

	public ProfileWrapper(String driver, String url) {
		sqlBase = new SQLiteBase(driver, url);
	}

	/* ==========The Readers=========== */
	private ResultSet getProfiles() {
		try {
			return sqlBase.executeQuery("SELECT * FROM Profiles");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

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
