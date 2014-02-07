package com.qait.tcautomation.util;

import static org.testng.Assert.assertNull;

import java.io.IOException;
import java.util.Properties;

public class PropertyReaderUtil {

	private Properties properties;

	public PropertyReaderUtil() {
		properties = new Properties();
	}
	
	/**
	 * This method will load all the property files and store all in a common
	 * object. If key in more than one property files are same then it will be
	 * overridden and value from the last one will be stores.
	 */
	
	// To-do : Need to store the paths in a file rather than hard coding it in code
	public void loadPropertyFiles() {
		String filePathArray[] = { "/loginPage.properties",
				"/homePage.properties",
				"/studentLearningObjectivesPage.properties","/classRoomObservationFormPage.properties" };

		for (String filepath : filePathArray) {
			Properties prop = new Properties();
			try {

				prop.load(PropertyReaderUtil.class
						.getResourceAsStream(filepath));
				properties.putAll(prop);

			} catch (IOException e) {

				System.out.println(e.getStackTrace());

				assertNull(e, TcAutomationUtil.getExceptionDescriptionString(e));
			} catch (Exception e) {

				System.out.println(e.getStackTrace());

				assertNull(e, TcAutomationUtil.getExceptionDescriptionString(e));
			}
		}
	}
	
	/**
	 * Method to get property value
	 * 
	 * @param key
	 *            key in the property file
	 * @return value corresponding to the passed key
	 */
	public String getPropertyValue(String key) {
		return properties.getProperty(key).trim();
	}
}
