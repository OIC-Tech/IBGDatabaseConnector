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
//			ibgResponseDB.addResponse("震惊了", "对啊，当时我也震惊了");
//			System.out.println(ibgResponseDB.searchResponse("震惊了").getString("Responses"));
			System.out.println(ibgResponseDB.isUnique("你好", "你好呀~", false));

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
