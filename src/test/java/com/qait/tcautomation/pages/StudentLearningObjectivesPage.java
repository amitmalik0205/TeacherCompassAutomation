package com.qait.tcautomation.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.testng.Assert;

import com.qait.tcautomation.util.PropertyReaderUtil;

/**
 * This page is corresponding to SLO home page
 * 
 * @author amitmalik
 * 
 */
public class StudentLearningObjectivesPage extends BasePage {

	public StudentLearningObjectivesPage(WebDriver driver,
			DesiredCapabilities capabilities, HttpCommandExecutor executor,
			PropertyReaderUtil propertReaderUtil) {
		super(driver, capabilities, executor, propertReaderUtil);
	}

	public StudentLearningObjectivesPage() {
		super();
	}
	
	/**
	 * Method will load the page containing SLO form
	 * 
	 * @return ClassRoomObservationFormPage object if page loaded successfully
	 *         otherwise time out exception will be thrown by selenium after
	 *         default seconds
	 */
	public ClassRoomObservationFormPage loadClassroomObservationFormPage() {

		studentLearningObjectivesUi.getCreateNewSloButton().click();

		waitForPageLoad(getPropertyValue("observation.form.page.title1"));
		
		List<WebElement> list = studentLearningObjectivesUi.getSloTableRowsList();
		Assert.assertNotNull(list, "liast is null");

		return new ClassRoomObservationFormPage(driver, capabilities, executor,
				propertReaderUtil);
	}
}
