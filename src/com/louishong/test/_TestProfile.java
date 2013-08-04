package com.louishong.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.louishong.database.ProfileWrapper;

public class _TestProfile {
	ProfileWrapper pw;

	@Before
	public void testInit() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		pw = new ProfileWrapper();
	}

	@Test
	public void testGetUserList() throws NumberFormatException, SQLException {
		int pageLength = 10;
		int indexSize = pw.getUserListSize(pageLength);
		System.out.println("Table Index length: " + indexSize / 10);
		for (int i = 1; i <= indexSize; i++) {
			System.out.println("Length of page " + i + ": " + pw.getUserList(10, i).size());
			System.out.println(pw.getUserList(10, i));
			System.out.println();
		}
	}

	@Test
	public void testGetEmail() throws SQLException {
		assertTrue("Email did not match", "honglouis@outlook.com".equals(pw.getEmail(1)));
		assertTrue("Email Name did not match", "honglouis@outlook.com".equals(pw.getEmail("刘加华")));
	}

	@Test
	public void testJob() throws SQLException {
		assertTrue("Job did not match", pw.getJob(1).equals("服务器管理员/程序员"));
		assertTrue("Job did not match", pw.getJob("刘加华").equals("服务器管理员/程序员"));
	}
	
	@Test
	public void testPhoneNumber() throws SQLException {
		assertTrue("Phone number did not match", pw.getPhoneNumber(1).equals("13910028039"));
		assertTrue("Phone number did not match", pw.getPhoneNumber("刘加华").equals("13910028039"));
	}
	
	@Test
	public void testPoints() throws UnsupportedEncodingException, SQLException {
		assertTrue("Points did not match", new Integer(24).equals(pw.getPoints(1)));
		assertTrue("Points did not match", new Integer(24).equals(pw.getPoints("刘加华")));
	}
	
	@Test
	public void testProfileAmount() throws NumberFormatException, SQLException {
		pw.getProfileAmount();
	}
	
	@Test
	public void testHasUser() throws SQLException {
		assertTrue("UID=1 exists, but has user returned false", pw.hasUser(1));
		assertTrue("UID=1 exists, but has user returned false", pw.hasUser("刘加华"));		
	}
	
}
