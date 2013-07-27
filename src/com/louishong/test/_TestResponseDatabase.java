package com.louishong.test;

import java.io.IOException;
import java.sql.SQLException;

import com.louishong.database.IBGWeixinServerResponseDatabase;


public class _TestResponseDatabase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IBGWeixinServerResponseDatabase ibgResponseDB = new IBGWeixinServerResponseDatabase();
			System.out.println(ibgResponseDB.searchResponse("今天谁做微报").getString("Responses"));
			System.out.println(ibgResponseDB.searchResponse("今天谁做微报").getString("Responses"));
			ibgResponseDB.cutConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("False Input!");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
