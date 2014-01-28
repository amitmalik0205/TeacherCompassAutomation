package com.qait.tcautomation.tests;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.tcautomation.enums.UserType;
import com.qait.tcautomation.pages.ClassRoomObservationFormPage;
import com.qait.tcautomation.pages.HomePage;
import com.qait.tcautomation.pages.LoginPage;
import com.qait.tcautomation.pages.StudentLearningObjectivesPage;

public class SloTest extends BaseTest {

	private LoginPage loginPage;
	private HomePage homePage;
	private StudentLearningObjectivesPage sloPage;
	private ClassRoomObservationFormPage classRoomObservationFormPage;

	@BeforeClass
	public void init() {
		loadProperties();
		loginPage = new LoginPage();
		loginPage.setPropertReaderUtil(propertyReaderUtil);
		driver = loginPage.initalizeDriver("");
		assertNotNull(driver, "Failed to initalize driver..");
	}

	@Test
	public void loginTest() {
		homePage = loginPage.login(UserType.TEACHER);
		assertNotNull(homePage);
		
		sloPage = homePage.getSloPage();
		classRoomObservationFormPage = sloPage.loadClassroomObservationFormPage();
		
		System.out.println("SLO page launched succesfully...");
		
		postComments();
	}

	/**
	 * Method to post comment directly not with note pad.
	 */
	public void postComments() {
		
		classRoomObservationFormPage.postCommentsForSlo();
	}
	
	@AfterClass
	public void tearDown() {
		loginPage.closeBrowser();
	}
}
