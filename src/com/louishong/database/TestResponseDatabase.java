package com.louishong.database;

import java.sql.SQLException;

public class TestResponseDatabase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IBGWeixinServerResponseDatabase ibgResponseDB = new IBGWeixinServerResponseDatabase();
			System.out.println(ibgResponseDB.searchResponse("����˭��΢��").getString("Responses"));
			System.out.println(ibgResponseDB.searchResponse("����˭��΢��").getString("Responses"));
			ibgResponseDB.cutConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("False Input!");
		}

	}
}
