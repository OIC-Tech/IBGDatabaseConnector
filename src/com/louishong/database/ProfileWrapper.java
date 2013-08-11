package com.louishong.database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * A wrapper for the connection with Profile table.
 * 
 * @author Louis Hong
 * @version 1.1
 */

public class ProfileWrapper {

	/**
	 * The SQLBase used to the raw database.
	 */
	public static SQLBase sqlBase;
	private static String sUrl = null;
	static {
		try {
			sUrl = DatabaseURL.getProfileURL();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor that initializes SQLBase with default drivers and URL.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	public ProfileWrapper() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {

		// The SQLite JDBC driver.
		String sDriver = "com.mysql.jdbc.Driver";
		// The URL of the Databases profiles.
		sqlBase = new SQLBase(sDriver, sUrl);
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
		sqlBase = new SQLBase(driver, url);
	}

	/* ==========The Readers=========== */
	/**
	 * Used to get the entire Profiles table.
	 * 
	 * @return ResuldSet of the ENTIRE Profiles table.
	 * @throws SQLException
	 * @throws Exception
	 */
	private ResultSet getAllProfiles(int length, int page) throws InvalidParameterException, SQLException {
		if (page == 0) {
			throw new InvalidParameterException("Cannot have page number lower then 1");
		}
		return sqlBase.fetchQuery(String.format("SELECT * FROM Profile LIMIT %s, %s", length * (page - 1), length));
	}

	public ResultSet searchProfile(String userName) throws SQLException {
		return sqlBase.fetchQueryPrepared("SELECT * FROM Profile WHERE UserName=?", userName);
	}

	public ResultSet searchProfile(int UID) throws SQLException {
		return sqlBase.fetchQueryPrepared("SELECT * FROM Profile WHERE UID=?", new Integer(UID).toString());
	}

	/**
	 * Searches for the existence of the user.
	 * 
	 * @param userName
	 * @return <boolean>boolean true for exists false for doesn't exists.
	 * @throws SQLException
	 */
	public Boolean hasUser(String userName) throws SQLException {
		ResultSet results = searchProfile(userName);
		return results.next();
	}

	/**
	 * Searches for the existence of the user.
	 * 
	 * @param UID
	 * @return <boolean>boolean true for exists false for doesn't exists.
	 * @throws SQLException
	 */
	public Boolean hasUser(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		return results.next();
	}

	/**
	 * Searches and returns the points a user has in the table.
	 * 
	 * @param userName
	 * @return String of the points the user has.
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int getPoints(String userName) throws SQLException, UnsupportedEncodingException {
		ResultSet results = searchProfile(userName);
		results.next();
		return results.getInt("Points");
	}

	/**
	 * Searches and returns the points a user has in the table.
	 * 
	 * @param UID
	 * @return String of the points the user has.
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int getPoints(int UID) throws SQLException, UnsupportedEncodingException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getInt("Points");
	}

	/**
	 * Searches in the table for the users email.
	 * 
	 * @param userName
	 * @return String email of the user.
	 * @throws SQLException
	 */
	public String getEmail(String userName) throws SQLException {
		ResultSet results = searchProfile(userName);
		results.next();
		return results.getString("Email");
	}

	/**
	 * Searches in the table for the users email.
	 * 
	 * @param UID
	 * @return String email of the user.
	 * @throws SQLException
	 */
	public String getEmail(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getString("Email");
	}

	/**
	 * Searches in the table for the users job.
	 * 
	 * @param userName
	 * @return Stringthe users job.
	 * @throws SQLException
	 */
	public String getJob(String userName) throws SQLException {
		ResultSet results = searchProfile(userName);
		results.next();
		return results.getString("Job");
	}

	/**
	 * Searches in the table for the users job.
	 * 
	 * @param UID
	 * @return Stringthe users job.
	 * @throws SQLException
	 */
	public String getJob(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getString("Job");
	}

	/**
	 * Searches in the table for the users phone number.
	 * 
	 * @param userName
	 * @return String containing the users phone number.
	 * @throws SQLException
	 */
	public String getPhoneNumber(String userName) throws SQLException {
		ResultSet results = searchProfile(userName);
		results.next();
		return results.getString("PhoneNumber");
	}

	/**
	 * Searches in the table for the users phone number.
	 * 
	 * @param UID
	 * @return String containing the users phone number.
	 * @throws SQLException
	 */
	public String getPhoneNumber(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getString("PhoneNumber");
	}

	/**
	 * Gives a 3 dimensional Map of the Profiles table.
	 * 
	 * @return Map<String, ArralyList<String>> with the userNames in the key and
	 *         all the information in the ArrayList.
	 * @throws SQLException
	 * @throws InvalidParameterException
	 */
	public Map<String, ArrayList<String>> getUserList(int length, int page) throws InvalidParameterException, SQLException {
		ResultSet results = getAllProfiles(length, page);

		Map<String, ArrayList<String>> mapResults = new HashMap<String, ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		while (results.next()) {
			data.clear();
			data.add(results.getString("Job"));
			data.add(results.getString("Email"));
			data.add(results.getString("Points"));
			data.add(results.getString("PhoneNumber"));
			if (mapResults.containsKey(results.getString("UserName"))) {
				mapResults.put(results.getString("UserName") + "_" + new Random().nextInt(), new ArrayList<String>(data));
			}
			mapResults.put(results.getString("UserName"), new ArrayList<String>(data));
		}
		return mapResults;

	}

	public int getProfileAmount() throws NumberFormatException, SQLException {
		ResultSet results = sqlBase.fetchQuery("SELECT COUNT(*) AS Length FROM Profile;");
		results.next();
		return new Integer(results.getString("Length"));

	}

	public int getUserListSize(int length) throws NumberFormatException, SQLException {
		return (int) Math.ceil((double) getProfileAmount() / length);
	}

	public String getNickName(String userName) throws SQLException {
		ResultSet results = searchProfile(userName);
		results.next();
		return results.getString("NickName");
	}

	public String getNickName(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getString("NickName");
	}

	public String getUserName(String userName) throws SQLException {
		ResultSet results = searchProfile(userName);
		results.next();
		return results.getString("UserName");
	}

	public String getUserName(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getString("UserName");
	}

	public byte[] getPasswordHash(String userName) throws SQLException {
		ResultSet results = searchProfile(userName);
		results.next();
		return results.getBytes("PasswordHash");
	}

	public byte[] getPasswordHash(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getBytes("PasswordHash");
	}
	
	public String getHashedPassword(String username) throws SQLException {
		ResultSet results = searchProfile(username);
		results.next();
		return results.getString("HashedPassword");
		
	}

	public String getHashedPassword(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getString("HashedPassword");
	}

	public boolean verifyPassword(String username, String password) throws SQLException {
		return BCrypt.checkpw(password, getHashedPassword(username));
	}
	
	public boolean hasAlphaPerm(String username) throws SQLException {
		ResultSet results = searchProfile(username);
		results.next();
		return results.getBoolean("AlphaPerm");
	}

	public boolean hasAlphaPerm(int UID) throws SQLException {
		ResultSet results = searchProfile(UID);
		results.next();
		return results.getBoolean("AlphaPerm");
	}

	/* ==========The Writers=========== */

	public void addUser(String username, String password, String nickName, String job, String email, int points, String phonenumber) throws SQLException {
		String encryptPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		sqlBase.executeQueryPrepared("INSERT INTO Profile (UserName, HashedPassword, NickName, Job, Email, Points, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)", username, encryptPassword, nickName, job, email, new Integer(points).toString(), phonenumber);		
	}

	public void addUser(int UID, String username, String password, String nickName, String job, String email, int points, String phonenumber) throws SQLException {
		String encryptPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		sqlBase.executeQueryPrepared("INSERT INTO Profile (UID, UserName, HashedPassword, NickName, Job, Email, Points, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new Integer(UID).toString() , username, encryptPassword, nickName, job, email, new Integer(points).toString(), phonenumber);		
	}

	public void removeUser(String username) throws SQLException {
		sqlBase.executeQueryPrepared("DELETE * WHERE UserName=?", new Integer(username).toString());

	}

	public void removeUser(int UID) throws SQLException {
		sqlBase.executeQueryPrepared("DELETE * WHERE UID=?", new Integer(UID).toString());

	}

	public void giveAlphaPerm(String username) throws SQLException {
		sqlBase.executeQueryPrepared("UPDATE Profile SET AlphaPerm=1 WHERE UserName=?", username);
	}

	public void giveAlphaPerm(int UID) throws SQLException {
		sqlBase.executeQueryPrepared("UPDATE Profile SET AlphaPerm=0 WHERE UID=?", new Integer(UID).toString());
	}

	public void recordIP(String username, String IP) throws SQLException {
		sqlBase.executeQueryPrepared("UPDATE Profile SET LastIP=? WHERE UserName=?", IP, username);
	}

	public void recordIP(int UID, String IP) throws SQLException {
		sqlBase.executeQueryPrepared("UPDATE Profile SET LastIP=? WHERE UID=?", IP, new Integer(UID).toString());
	}

}
