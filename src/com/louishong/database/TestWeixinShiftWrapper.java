package com.louishong.database;

import java.sql.SQLException;

public class TestWeixinShiftWrapper {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		WeixinShiftWrapper weixinShift = new WeixinShiftWrapper();
		weixinShift.updateDatebase();

	}

}
