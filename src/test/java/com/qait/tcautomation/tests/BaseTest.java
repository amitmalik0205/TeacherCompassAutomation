package com.qait.tcautomation.tests;

import org.openqa.selenium.WebDriver;

import com.qait.tcautomation.util.PropertyReaderUtil;

public class BaseTest {

	protected WebDriver driver;
	protected PropertyReaderUtil propertyReaderUtil;

	protected void loadProperties() {
		propertyReaderUtil = new PropertyReaderUtil();
		propertyReaderUtil.loadPropertyFiles();
	}
}
