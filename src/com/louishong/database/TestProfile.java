package com.louishong.database;

import java.util.*;

public class TestProfile {

	public static void main(String[] args) {
		ProfileWrapper profile = new ProfileWrapper();
		System.out.println(profile.getUserList());
		Map<String, ArrayList<String>> userList = profile.getUserList();
		Iterator<String> userListKeys = userList.keySet().iterator();
		while (userListKeys.hasNext()) {
			String key = userListKeys.next();
			System.out.print(key + " :");
			System.out.println(userList.get(key));
		}

	}

}
