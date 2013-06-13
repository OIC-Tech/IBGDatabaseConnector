package com.louishong.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class WeixinShiftWrapper {

	public static String sDriver = "org.sqlite.JDBC";

	final public static String sUrl = DataBaseLocation.profileURL;

	// Where the file is stored on The server
	// final public static String profileURL =
	// "jdbc:sqlite:C:\\OIC\\database\\Profiles.sqlite";

	public static SQLiteBase sqlBase;
	
	public WeixinShiftWrapper() {
		sqlBase = new SQLiteBase(sDriver, sUrl);
	}
	
	public WeixinShiftWrapper(String driver, String url) {
		sqlBase = new SQLiteBase(driver, url);
	}

	public ResultSet getWeixinShift() throws SQLException {
		return sqlBase.executeQuery("SELECT * FROM WeixinShift");
	}

	public LocalDate dateConverter(String stringDate) {
		String dateFormatter = "yy-MM-dd";
		return LocalDate.parse(stringDate, DateTimeFormat.forPattern(dateFormatter));
	}

	public ArrayList<LocalDate> getNextShifts(String name) throws SQLException, NullPointerException {
		ResultSet results = getWeixinShift();
		ArrayList<LocalDate> weixinShifts = new ArrayList<LocalDate>();

		try {
			while (results.next()) {
				String resultName = results.getString("ChineseName");
				if (resultName.equals(name)) {
					weixinShifts.add(dateConverter(results.getString("NextShift")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return weixinShifts;
	}

	public boolean isShiftDaysAfter(String name, int days) {
		LocalDate today = new LocalDate();
		today = today.plusDays(days);
		try {
			ArrayList<LocalDate> userWeixinShifts = getNextShifts(name);
			Iterator<LocalDate> shiftIterator = userWeixinShifts.iterator();

			while (shiftIterator.hasNext()) {
				LocalDate shift = shiftIterator.next();
				if (shift.getYear() == today.getYear()) {
					if (shift.getMonthOfYear() == today.getMonthOfYear()) {
						if (shift.getDayOfMonth() == today.getDayOfMonth()) {
							return true;
						}
					}
				}
			}

		} catch (Exception e) {
		}
		return false;
	}

	public void setWeixinShift(String name, LocalDate oldDate, LocalDate newDate) {
		// String stringNewDate = dateFormatter(newDate);
		// String stringOldDate = dateFormatter(oldDate);
		try {
			sqlBase.executeQuery(String.format("UPDATE WeixinShift SET NextShift='%s' WHERE ChineseName='%s' AND NextShift='%s'", newDate, name, oldDate));
		} catch (SQLException e) {
		}
	}

	public void updateDatebase() throws SQLException {
		ResultSet results = getWeixinShift();

		resultLoop: while (results.next()) {
			System.out.print("[" + results.getString("ChineseName") + "]");

			LocalDate today = new LocalDate();
			LocalDate nextShift = null;
			int shiftPeriod;

			try {
				nextShift = dateConverter(results.getString("NextShift"));
				shiftPeriod = Integer.parseInt(results.getString("ShiftPeriod"));
			} catch (NullPointerException e) {
				System.out.println("......NO SHIFTS!");
				continue resultLoop;
			}

			while (nextShift.isBefore(today)) {
				setWeixinShift(results.getString("ChineseName"), nextShift, nextShift.plusDays(shiftPeriod));
				nextShift = nextShift.plusDays(shiftPeriod);
				System.out.println("......Updated Shift");
				continue resultLoop;
			}

			System.out.println("......No Changes");
		}
	}

}
