package com.louishong.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.louishong.database.SQLiteBase;

public class _TestSQLite {

	public static String sDriver = "org.sqlite.JDBC";

	final public static String sUrl = "jdbc:sqlite:/Users/honglouis/Documents/Github Repo/OICWebsite/database/Profiles.sqlite";

	// Where the file is stored on The server
	// final public static String profileURL =
	// "jdbc:sqlite:C:\\OIC\\database\\Profiles.sqlite";

	public static void main(String args[]) {
		try {
			SQLiteBase sqlBase = new SQLiteBase(sDriver, sUrl);

			// ArrayList<String> params = new ArrayList<String>();
			// params.add("刘加华");
			// params.add("2013-06-07");

			try {
				sqlBase.executeQueryPrepared(
						"UPDATE WeixinShift SET NextShift=? WHERE ChineseName=? AND NextShift=?",
						"2013-06-07", "刘加华", "2013-06-28");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ResultSet results = sqlBase.fetchQuery("SELECT * FROM WeixinShift");
			while (results.next()) {
				System.out.println(results.getString("ChineseName"));
				System.out.println(results.getString("NextShift"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
