package com.louishong.test;

import static org.junit.Assert.assertFalse;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.louishong.database.WeixinShiftWrapper;

public class _TestWeixinShiftWrapper {

	private static WeixinShiftWrapper shiftWrapper;

	@Before
	public void testInit() {
		try {
			shiftWrapper = new WeixinShiftWrapper();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetNextShifts() throws NullPointerException, SQLException { 
		System.out.println(shiftWrapper.getNextShifts(33));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSetWeixinShift() throws SQLException {
		Date oldDate = new Date(90, 0, 1);
		Date newDate= new Date(113, 4, 1);
		System.out.println(oldDate);
		System.out.println(newDate);
		shiftWrapper.setWeixinShift(33, oldDate, newDate);
		assertFalse("ShiftDate did not change", oldDate.toString().equals(shiftWrapper.getNextShifts(33)));
		shiftWrapper.setWeixinShift(33, newDate, oldDate);
	}
	
	@Test
	public void testGetTodaysShifts () throws SQLException {
		shiftWrapper.getTodaysShifts();
	}
	
	@Test
	public void testUpdateDatabase() throws SQLException { 
		shiftWrapper.updateDatebase();
	}

}
