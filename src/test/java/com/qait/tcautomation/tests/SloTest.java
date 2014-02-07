package com.qait.tcautomation.tests;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

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
	 * new SLO by selection the window 3. Post comments directly and check the
	 * status in left pane('completed') 4. Post comments through note pad by
	 * selecting objectives from pop up 5. check whether note pad comments are
	 * reflected in text areas or not. 6. Share draft to principal
	 */
	@Test
	public void sloTeacherShareDraftTest() {

		homePage = loginPage.login(UserType.TEACHER);
		assertNotNull(homePage);

		sloPage = homePage.getSloPage();
		sloPage.loadSloWindow();
		classRoomObservationFormPage = sloPage
				.loadClassroomObservationFormPage();

		postComments();

		postCommentThroughNotepad();

		// classRoomObservationFormPage.addArtifacts();

		classRoomObservationFormPage.shareDraftToPrincipal();

		homePage.signOut();
	}
	
	
	/**
	 * This test does the following things: 1.Login as Principal 2.Click on the
	 * first notification in the notification bubble. 3.post feedback 4.Share
	 * again it with teacher and sign out
	 */
	@Test(dependsOnMethods = { "sloTeacherShareDraftTest" })
	public void sloPrincipalShareDraftTest() {

		homePage = loginPage.login(UserType.PRINCIPAL);

		classRoomObservationFormPage = homePage.viewNotificationByIndex(0);

		classRoomObservationFormPage.postCommentsForSlo(UserType.PRINCIPAL);

		classRoomObservationFormPage.shareDraftToTeacher();

		homePage.signOut();
	}

	
	/**
	 * This test does the following things: 1.Login as Teacher 2.open the slo
	 * list for window by selecting it from drop down list 3.Check for the
	 * status of slo 4.Edit the slo 5.Check whether principal feedback is
	 * reflect 6.Share it again with Principal and sign out
	 */
	@Test(dependsOnMethods = { "sloPrincipalShareDraftTest" })
	public void sloTeacherShareDraftAgainTest() {

		homePage = loginPage.login(UserType.TEACHER);
		sloPage = homePage.getSloPage();
		sloPage.loadSloWindow();

		/*
		 * Now check whether slo status is
		 * 'slo.status.after.principal.share.draft' property value. After
		 * principal shares draft its status changes to
		 * 'slo.status.after.principal.share.draft' property
		 */
		boolean b = sloPage.isSloInProgress(1);
		assertTrue(b, "SLO status is not correctly reflected..");

		// Now edit the SLO
		classRoomObservationFormPage = sloPage.editSloFormGrid(1);

		// Check whether principal feedback is reflected to teacher or not
		classRoomObservationFormPage.checkForPrincipalFeedBack();

		classRoomObservationFormPage.shareDraftToPrincipal();

		homePage.signOut();
	}
	
	
	/**
	 * This test does the following things: 1.Login as Teacher 2.open the slo
	 * list for window by selecting it from drop down list 3.Check for the
	 * status of slo 4.Edit the slo from grid 6.Approve slo and score it
	 */
	@Test(dependsOnMethods = { "sloTeacherShareDraftAgainTest" })
	public void sloPrincipalScoreTest() {
		homePage = loginPage.login(UserType.PRINCIPAL);
		sloPage = homePage.getSloPage();
		sloPage.loadSloWindow();

		/*
		 * Now check whether slo status is 'slo.progress.status' property value.
		 * After principal shares draft its status changes to
		 * 'slo.progress.status' property
		 */
		boolean b = sloPage.isSloInDraft(1);
		assertTrue(b, "SLO status is not correctly reflected..");

		// Edit the SLO
		classRoomObservationFormPage = sloPage.editSloFormGrid(1);
		// Approve SLO
		classRoomObservationFormPage.approveSlo();
		// Score SLO
		classRoomObservationFormPage.scoreSlo();
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
