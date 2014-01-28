package com.qait.tcautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;

import com.qait.tcautomation.util.PropertyReaderUtil;

public class HomePage extends BasePage {

	public HomePage() {
		super();
	}
	
	public HomePage(WebDriver driver, DesiredCapabilities capabilities,
			HttpCommandExecutor executor, PropertyReaderUtil propertReaderUtil) {
		
		super(driver, capabilities, executor, propertReaderUtil);
		
	}
	
	/**
	 * Method will load SLO home page
	 * 
	 * @return If page loaded then return StudentLearningObjectivesPage object
	 *         otherwise exception will be thrown
	 */
	public StudentLearningObjectivesPage getSloPage() {

		loadPage(getPropertyValue("slo.url"), getPropertyValue("slo.title"));

		return new StudentLearningObjectivesPage(driver, capabilities,
				executor, propertReaderUtil);
	}

}
