package com.louishong.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

/**
 * To get peoples Weixin News shift data
 * 
 * @author Louis Hong
 * @version 1.0
 */
public class WeixinShiftWrapper {


	/**
	 * The SQLBase used to the raw database.
	 */
	public static SQLiteBase sqlBase;

	/**
	 * Constructor that initializes SQLBase with default drivers and URL.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException 
	 */
	public WeixinShiftWrapper() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException, IOException {

		//The SQLite JDBC driver.
		String sDriver = "org.sqlite.JDBC";
		//The URL of the Databases profiles.
		final String sUrl = DatabaseLocation.getProfileURL();
		sqlBase = new SQLiteBase(sDriver, sUrl);
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
	public WeixinShiftWrapper(String driver, String url)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		sqlBase = new SQLiteBase(driver, url);
	}

	/**
	 * Gets the whole WeixinShift table from the database.
	 * 
	 * @return ResultSet from the WeixinShift table.
	 * @throws SQLException
	 */
	public ResultSet getWeixinShift() throws SQLException {
		return sqlBase.fetchQuery("SELECT * FROM WeixinShift");
	}

	/**
	 * Converts date from formatted String to LocalDate.
	 * 
	 * @param stringDate
	 * @return LocalDate
	 */
	public LocalDate dateConverter(String stringDate) {
		String dateFormatter = "yy-MM-dd";
		return LocalDate.parse(stringDate, DateTimeFormat.forPattern(dateFormatter));
	}

	/**
	 * Get's all the shifts that the user has.
	 * 
	 * @param name
	 * @return ArralyList<LocalDate>
	 * @throws SQLException
	 * @throws NullPointerException
	 */
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

	/**
	 * Checking if the users shift is days after or days before the current
	 * date.
	 * 
	 * @param name
	 * @param days
	 * @return boolean of if shift is days after or before.
	 */
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

	/**
	 * Changes the users shift from oldDate to newDate
	 * 
	 * @param name
	 * @param oldDate
	 * @param newDate
	 * @throws SQLException
	 */
	public void setWeixinShift(String name, String oldDate, String newDate) throws SQLException {
		sqlBase.executeQueryPrepared("UPDATE WeixinShift SET NextShift=? WHERE ChineseName=? AND NextShift=?", newDate, name, oldDate);
	}

	/**
	 * Loops through the whole database checking for outdated shift dates. If an
	 * outdated shift is spotted then the method will add the period to the
	 * shift date until the date is before the current date.
	 * 
	 * @throws SQLException
	 */
	public void updateDatebase() throws SQLException {
		ResultSet results = getWeixinShift();

		resultLoop : while (results.next()) {
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
				setWeixinShift(results.getString("ChineseName"), nextShift.toString(), nextShift.plusDays(shiftPeriod).toString());
				nextShift = nextShift.plusDays(shiftPeriod);
				System.out.println("......Updated Shift");
				continue resultLoop;
			}

			System.out.println("......No Changes");
		}
	}

}
