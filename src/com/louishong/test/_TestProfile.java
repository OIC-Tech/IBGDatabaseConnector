package com.louishong.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import com.louishong.database.ProfileWrapper;


public class _TestProfile {

	public static void main(String[] args) {
		ProfileWrapper profile;
		try {
			profile = new ProfileWrapper();
			System.out.println(profile.getUserList());
			Map<String, ArrayList<String>> userList = profile.getUserList();
			Iterator<String> userListKeys = userList.keySet().iterator();
			while (userListKeys.hasNext()) {
				String key = userListKeys.next();
				System.out.print(key + " :");
				System.out.println(userList.get(key));
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
