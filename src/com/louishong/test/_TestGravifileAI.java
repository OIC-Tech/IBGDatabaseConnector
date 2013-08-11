package com.louishong.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.louishong.database.GravifileAIDatabase;

public class _TestGravifileAI {

	GravifileAIDatabase aiDatabase;

	@Before
	public void testInit() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		aiDatabase = new GravifileAIDatabase();
	}

	@Test
	public void testIsUnique() throws SQLException {
		assertFalse("This conversation does exist", aiDatabase.isUnique("你好", "你好呀~", false));
		assertTrue("This conversation doesn't exist", aiDatabase.isUnique("Th1s 1s ju5T a T35T", "Y0UR DAMN R1GHT", false));
	}

	@Test
	public void testLength() throws SQLException {
		aiDatabase.length("你好");
	}

	@Test
	public void testAddConversation() throws SQLException {
		aiDatabase.addConversation("TESTING_01", "GINTSET_01", false, false, false, "IamHD");
		assertFalse("Failed to add Conversion", aiDatabase.isUnique("TESTING_01", "GINTSET_01", false));
	}

	@Test
	public void testSearchResonse() throws SQLException {
		ResultSet results = aiDatabase.searchResponse("我去");
		results.next();
		assertTrue("Couldn't find the response to the conversation", results.getString("Responses").equals("怎么样,厉害吧!"));
	}

	@Test
	public void testDeleteConversation() throws SQLException {
		aiDatabase.deleteConversation("TESTING_01", "GINTSET_01", false, false);
		assertTrue("Failed to delete Conversion", aiDatabase.isUnique("TESTING_01", "GINTSET_01", false));
	}

}
