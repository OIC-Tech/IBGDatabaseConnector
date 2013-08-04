package com.louishong.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 * Declares where Databases are.
 * 
 * @author root
 * @version 1.0
 */
public class DatabaseURL {

	private static Logger logger = Logger.getLogger(DatabaseURL.class.getName());
	private static String configDir = System.getenv("GravifileConfig");

	static {
		try {
			if (configDir == (null)) {
				configDir = "~/DBConnector";
			}
			Handler fileHandler = new FileHandler(configDir  + "/database" + "/DBConnector.log");
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (Exception e) {
			
			logger.warning("No database config folder, setting up config folder!");
			try {
				createNewProperty();
			} catch (Exception e1) {
				logger.severe(e1.getMessage());
				logger.severe("Failed to setup the log file. log file is disabled");
			} 
		}
	}

	// final public static String profileURL =
	// "jdbc:sqlite:/Users/honglouis/Documents/Github Repo/OICWebsite/database/profiles.sqlite";

	// Where the file is stored on The server
	// final public static String profileURL =
	// "jdbc:sqlite:F:\\OIC\\OICWebsite\\database\\profiles.sqlite";

	// final public static String responsesURL =
	// "jdbc:sqlite:/Users/honglouis/Documents/Github Repo/OICWebsite/database/response.sqlite";

	// Where the file is stored on The server
	// final public static String responsesURL =
	// "jdbc:sqlite:F:\\OIC\\OICWebsite\\database\\response.sqlite";

	// ProfileURL=jdbc:sqlite:/Users/honglouis/Documents/Github
	// Repo/OICWebsite/database/profiles.sqlite
	// ResponsesURL=jdbc:sqlite:/Users/honglouis/Documents/Github
	// Repo/OICWebsite/database/response.sqlite

	public static String getProfileURL() throws IOException {
		Properties properties = new Properties();

		try {
			logger.info("Loading Database Properties from Config file");
			// load a properties file
			properties.load(new FileInputStream(configDir  + "/database" + "/database.properties"));
			logger.info("Config successfully loaded!");

			// get the property value and print it out
			return properties.getProperty("ProfileURL");
		} catch (IOException ex) {
			logger.warning("Config file failed to load, maybe the file does not exist.");
			logger.info("Creating a new Config file");
			try {
				createNewProperty();
			} catch (Exception e) {
				logger.severe("Failed to create Properties Config!");
				throw new IOException("Failed to create Properties Config!");
			}
			logger.info("Config file created!");
			return getProfileURL();
		}
	}

	public static String getConversationURL() throws IOException {
		Properties properties = new Properties();

		try {
			// load a properties file
			properties.load(new FileInputStream(configDir  + "/database" + "/database.properties"));

			// get the property value and print it out
			return properties.getProperty("GravityAIURL");
		} catch (IOException ex) {
			try {
				logger.warning("Config file failed to load, maybe the file does not exist.");
				logger.info("Creating a new Config file");
				createNewProperty();
			} catch (Exception e) {
				logger.severe("Failed to create Properties Config!");
				throw new IOException("Failed to create Properties Config!");
			}
			logger.info("Config file created!");
			return getConversationURL();
		}
	}

	public static void createNewProperty() throws FileNotFoundException, IOException {
		Properties properties = new Properties();

		// set the properties value
		properties.setProperty("ProfileURL", "jdbc:mysql://localhost:3306/GravifileDatabase?user=&password=&characterEncoding=UTF-8");
		properties.setProperty("GravityAIURL", "jdbc:mysql://localhost:3306/GravityAI?user=&password=&characterEncoding=UTF-8");
		logger.info("Config Properties: \n" + properties.toString());

		// save properties to project config folder
		logger.info("Checking if Config directory exists or not.");
		File configFolder = new File(configDir + "/database");
		if (!configFolder.exists()) {
			configFolder.mkdir();
			logger.info("Config directory doesn't exist, created new one!");
		}
		properties.store(new FileOutputStream(configDir  + "/database" + "/database.properties"), null);
		logger.info("The Config has been created, properties has been stored at file :\n" + configFolder.getAbsolutePath());

	}

}