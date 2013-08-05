package com.louishong.database;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.sql.PreparedStatement;

import java.sql.Connection;

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
	public static SQLBase sqlBase;
	static String sUrl = null;
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
	public WeixinShiftWrapper() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {

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
	public WeixinShiftWrapper(String driver, String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		sqlBase = new SQLBase(driver, url);
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

	public ResultSet searchShift(int UID) throws SQLException {
		return sqlBase.fetchQueryPrepared("SELECT * FROM WeixinShift WHERE UID=?", new Integer(UID).toString());
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
	public ArrayList<LocalDate> getNextShifts(int UID) throws SQLException, NullPointerException {
		ResultSet results = searchShift(UID);
		ArrayList<LocalDate> weixinShifts = new ArrayList<LocalDate>();

		while (results.next()) {
			weixinShifts.add(new LocalDate(results.getDate("NextShift")));
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
	public boolean isShiftDaysAfter(int UID, int days) {
		LocalDate today = new LocalDate();
		today = today.plusDays(days);
		try {
			ArrayList<LocalDate> userWeixinShifts = getNextShifts(UID);
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
	public void setWeixinShift(int UID, Date oldDate, Date newDate) throws SQLException {
		Connection sqlConnection = sqlBase.con;
		PreparedStatement statement = sqlConnection.prepareStatement("UPDATE WeixinShift SET NextShift=? WHERE UID=? AND NextShift=?");
		statement.setDate(1, newDate);
		statement.setInt(2, UID);
		statement.setDate(3, oldDate);
		statement.executeUpdate();
	}

	public void setWeixinShift(int UID, LocalDate oldDate, LocalDate newDate) throws SQLException {
		Connection sqlConnection = sqlBase.con;
		PreparedStatement statement = sqlConnection.prepareStatement("UPDATE WeixinShift SET NextShift=? WHERE UID=? AND NextShift=?");
		statement.setDate(1, new Date(newDate.toDate().getTime()));
		statement.setInt(2, UID);
		statement.setDate(3, new Date(oldDate.toDate().getTime()));
		statement.executeUpdate();
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
			System.out.print("[ UID:" + results.getString("UID") + "]");

			LocalDate today = new LocalDate();
			LocalDate nextShift = null;
			int shiftPeriod;

			try {
				nextShift = new LocalDate(results.getDate("NextShift"));
				shiftPeriod = Integer.parseInt(results.getString("ShiftPeriod"));
			} catch (NullPointerException e) {
				System.out.println("......NO SHIFTS!");
				continue resultLoop;
			}

			while (nextShift.isBefore(today)) {
				setWeixinShift(results.getInt("UID"), nextShift, nextShift.plusDays(shiftPeriod));
				nextShift = nextShift.plusDays(shiftPeriod);
				System.out.println("......Updated Shift");
				continue resultLoop;
			}

			System.out.println("......No Changes");
		}
	}

	public ArrayList<Integer> getTodaysShifts() throws SQLException {
		Connection con = sqlBase.con;
		PreparedStatement statement = con.prepareStatement("SELECT * FROM WeixinShift WHERE NextShift=?");
		statement.setDate(1, new Date(new LocalDate().toDate().getTime()));
		ResultSet results = statement.executeQuery();
		ArrayList<Integer> UIDs = new ArrayList<Integer>();
		while (results.next()) {
			UIDs.add(results.getInt("UID"));
		}
		return UIDs;

	}

}
