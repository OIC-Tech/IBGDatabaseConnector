package com.louishong.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.LocalDate;

import com.louishong.database.WeixinShiftWrapper;

public class _TestWeixinShiftWrapper {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		try {
			WeixinShiftWrapper weixinShift;
			weixinShift = new WeixinShiftWrapper();
//			System.out.println(weixinShift.getWeixinShift());

			LocalDate oldDate = LocalDate.parse("2013-06-07");
			LocalDate today = LocalDate.now();
			weixinShift.setWeixinShift("Áõ¼Ó»ª", oldDate.toString(),
					today.toString());
			ResultSet results = weixinShift.getWeixinShift();
			while (results.next()) {
				System.out.println(results.getString("ChineseName"));
				try {
					System.out.println(weixinShift.getNextShifts(results
							.getString("ChineseName")));
				} catch (NullPointerException e) {
					System.out.println("[No Shifts]");
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
