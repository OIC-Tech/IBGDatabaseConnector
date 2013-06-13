package com.louishong.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestSQLite {

	public static String sDriver = "org.sqlite.JDBC";

	final public static String sUrl = "jdbc:sqlite:/Users/honglouis/Documents/Github Repo/OICWebsite/db/Profiles.sqlite";

	// Where the file is stored on The server
	// final public static String profileURL =
	// "jdbc:sqlite:C:\\OIC\\database\\Profiles.sqlite";

	public static SQLiteBase sqlBase1 = new SQLiteBase(sDriver, sUrl);
	public static SQLiteBase sqlBase2 = new SQLiteBase(sDriver, sUrl);

	public static void main(String args[]) {
		ResultSet result = null;
		try {
			result = sqlBase1.executeQuery("SELECT * FROM WeixinShift");
			result = sqlBase1.executeQuery("SELECT * FROM WeixinShift");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(result.getString("ChineseName"));
//			System.out.println(result1.getRow());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("HI");
		
	}
}
