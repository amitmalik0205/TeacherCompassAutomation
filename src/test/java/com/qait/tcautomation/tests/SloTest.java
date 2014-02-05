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

	/**
	 * This test does the following things: 1. Log in as a teacher. 2. Launch
	 * new SLO 3. Post comments directly and check the status in left
	 * pane('completed') 4. Post comments through note pad by selecting
	 * objectives from pop up 5. check whether note pad comments are reflected
	 * in text areas or not. 6. Share draft to principal
	 */
	@Test
	public void sloTeacherShareDraftTest() {
		homePage = loginPage.login(UserType.TEACHER);
		assertNotNull(homePage);

		sloPage = homePage.getSloPage();
		classRoomObservationFormPage = sloPage
				.loadClassroomObservationFormPage();

		System.out.println("SLO page launched succesfully...");

		postComments();

		postCommentThroughNotepad();

		// classRoomObservationFormPage.addArtifacts();

		classRoomObservationFormPage.shareDraftToPrincipal();

		homePage.signOut();
	}

	/**
	 * This method does the following things: 1. Launch the observation 2.
	 * Comments are made through note pad 3. Check that comments are reflected
	 * in text areas of objective under all domains.
	 */
	public void postCommentThroughNotepad() {
		classRoomObservationFormPage.postCommentsThroughNotepad();

		/*
		 * Now we will check whether comments posted through the note pad are
		 * appearing in the text areas of objectives for all domains.
		 */
		classRoomObservationFormPage.checkNotepadComments();
	}

	/**
	 * Method to post comment directly not with note pad.
	 */
	public void postComments() {

		classRoomObservationFormPage.postCommentsForSlo(UserType.TEACHER);
	}

	@AfterClass
	public void tearDown() {
		loginPage.closeBrowser();
	}
}
