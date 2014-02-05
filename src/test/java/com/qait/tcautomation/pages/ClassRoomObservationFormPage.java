package com.qait.tcautomation.pages;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;

import com.qait.tcautomation.enums.UserType;
import com.qait.tcautomation.util.PropertyReaderUtil;

public class ClassRoomObservationFormPage extends BasePage {

	public ClassRoomObservationFormPage(WebDriver driver,
			DesiredCapabilities capabilities, HttpCommandExecutor executor,
			PropertyReaderUtil propertReaderUtil) {
		super(driver, capabilities, executor, propertReaderUtil);
	}

	public ClassRoomObservationFormPage() {
		super();
	}

	/**
	 * Method to post direct comments for SLO 1. Post comment directly 2. Post
	 * overall comments 3. Check if comments are updated successfully
	 */
	public void postCommentsForSlo(UserType userType) {

		List<Integer> list = prepareDomainPositionListForObservation();

		String placeholder = null;
		String comments = null;
		String overallComments = null;

		switch (userType) {

		case TEACHER:
			placeholder = getPropertyValue("observation.form.page.teacher.direct.comment.placeholder");
			comments = getPropertyValue("observation.form.page.teacher.direct.comment");
			overallComments = getPropertyValue("observation.form.page.teacher.overall.comment");
			break;

		case PRINCIPAL:
			placeholder = getPropertyValue("observation.form.page.principal.direct.feedback.placeholder");
			comments = getPropertyValue("observation.form.page.principal.direct.feedback.comment");
			overallComments = getPropertyValue("observation.form.page.principalr.overall.comment");
			break;

		default:
			break;
		}

		// Score domains one by one
		for (int i : list) {

			String domainAnchorElementText = clickDomainAnchorsByPosition(i);

			postComments(placeholder, comments);

			postOverallComments(overallComments);

			classRoomObservationFormPageUi.clickSaveButton();

			if (userType == UserType.TEACHER) {
				checkForComments(i, domainAnchorElementText);
			} else {
				waitForDivMessageToFadeOut();
			}
		}
	}

	/**
	 * Check whether status changes to 'completed' under the domain <div>. If
	 * 'completed' is shown then it is successful.
	 * 
	 * @param index
	 *            list index
	 * @param domainName
	 *            name of domain
	 */
	private void checkForComments(int index, String domainName) {

		String reflectedScore = classRoomObservationFormPageUi
				.getStatusElemtFromLeftPanel(index).getText();

		assertThat(
				"Assertion Error : Comments for all objectives are not posted...",
				reflectedScore, containsString("completed"));

		System.out.println("Comments updated using save button for domain :"
				+ domainName);
	}

	/**
	 * This method returns a list of position for domain anchor tags in the <li>
	 * elements
	 * 
	 * @return list
	 */
	public List<Integer> prepareDomainPositionListForObservation() {

		List<WebElement> liElementList = classRoomObservationFormPageUi
				.getLiElementList();

		System.out.println("Total <li> elements are :" + liElementList.size());

		int liElementCounter = 0;
		boolean isDomainNavHeaderReached = false;
		List<Integer> domainPositions = new LinkedList<Integer>();

		for (WebElement element : liElementList) {

			liElementCounter++;

			String listElementText = element.getText();
			System.out.println("<li> text :" + listElementText);
			System.out.println();

			/*
			 * Since domains are below the 'Domain' heading skip all <li> until
			 * it reaches
			 */
			if (!isDomainNavHeaderReached && listElementText != null
					&& !listElementText.equals("Domains")) {
				continue;
			}

			isDomainNavHeaderReached = true;

			// Skip <li> for Domain heading and divider
			String className = element.getAttribute("class").trim();
			if (className != null
					&& (className.equals("divider") || className
							.equals("nav-header"))) {
				continue;
			}

			classRoomObservationFormPageUi
					.getDomainAnchorByPositionFromLeftPanel(liElementCounter);

			domainPositions.add(liElementCounter);

		}

		return domainPositions;
	}

	/**
	 * Method will click on the domain anchor link
	 * 
	 * @param pos
	 *            position of the <a> tag of domain in
	 *            <ul>
	 * @return String containing the name of domain
	 */
	private String clickDomainAnchorsByPosition(int pos) {

		System.out.println("Inside clickDomainAnchorsByPosition( )...");

		WebElement domainAnchorElement = classRoomObservationFormPageUi
				.getDomainAnchorByPositionFromLeftPanel(pos);

		domainAnchorElement.click();

		// String domainAnchorElementText = driver.findElement(
		// By.xpath("//div[@id='leftpanelnew']/ul/li[" + pos + "]/a/p"))
		// .getText();

		String domainAnchorElementText = classRoomObservationFormPageUi
				.getDomainAnchorTextByPositionFromLeftPanel(pos).getText();

		System.out.println("Domain clicked is : " + domainAnchorElementText);

		return domainAnchorElementText;
	}

	/**
	 * Method to post comment for each objective
	 * 
	 * @param placeHolder
	 *            place holder for text area where comments are to be posted
	 * @param comments
	 *            comments to post
	 */
	public void postComments(String placeHolder, String comments) {

		List<WebElement> listOfCommentTextArea = classRoomObservationFormPageUi
				.getCommentTextAreaListByPlaceHolder(placeHolder);

		assertThat("Assertion Error : No text area found for comment..",
				listOfCommentTextArea.size(), greaterThan(0));

		for (WebElement element : listOfCommentTextArea) {
			element.sendKeys(comments);
		}

		System.out.println("Comments are posted...");
	}

	/**
	 * Method to post overall comments
	 */
	public void postOverallComments(String comments) {

		WebElement element = classRoomObservationFormPageUi
				.getOverallCommentTextArea();

		element.sendKeys(comments);

		System.out.println("Overall Comments are posted...");
	}

	/**
	 * This method will post the comments through note pad. It will post the
	 * comment for all the objectives in all under all domains i.e for all the
	 * objectives shown in the pop up except domains itself(i.e not post overall
	 * comments).
	 */
	public void postCommentsThroughNotepad() {

		classRoomObservationFormPageUi.clickNotePadButton();

		writeCommentsThroughNotepad();

		classRoomObservationFormPageUi.clickLinkToStandardsButton();

		selectStandardAreaFromPopUp();

		System.out.println("Clicking save button of pop up...");

		classRoomObservationFormPageUi.clickSaveButtonOfPopUp();

		waitForParagraphMessageToFadeOut();

		System.out.println("Note pad comments posted...");
	}

	/**
	 * Method to write comments in Text area of note pad.
	 */
	private void writeCommentsThroughNotepad() {

		WebElement commentTextArea = classRoomObservationFormPageUi
				.getNotepadCommentTextArea();

		commentTextArea
				.sendKeys(getPropertyValue("observation.form.page.teacher.notepad.comment"));
	}

	/**
	 * Method to select Standard areas from the pop up. This method will check
	 * all the check boxes except Domain check box(at the top of each section
	 * e.g 'Learning Environment', 'Instruction')
	 */
	private void selectStandardAreaFromPopUp() {

		List<WebElement> popUpTable = classRoomObservationFormPageUi
				.getStandardLinkCheckboxList();

		assertNotNull(popUpTable,
				"Assertion Error: table of objective in pop up not found...");
		assertThat("Assertion Error : No element found in pop table..",
				popUpTable.size(), greaterThan(0));

		for (WebElement element : popUpTable) {
			element.click();
		}
	}

	/**
	 * This method will wait until message 'Your changes have been saved
	 * successfully' to is shown and then fade out. This message is shown in a
	 * <p>
	 * element when we post comments through note pad
	 */
	private void waitForParagraphMessageToFadeOut() {

		System.out.println("Inside waitForParagraphMessageToFadeOut()..");

		WebElement e = classRoomObservationFormPageUi
				.waitForParagraphMessageToAppear();

		while (e.isDisplayed()) {
			System.out
					.println("inside while..Waiting for message in <p> to fade out");
		}
	}

	/**
	 * In this method we will check whether comments posted through the note pad
	 * are appearing in the text areas of objectives for all domains.
	 */
	public void checkNotepadComments() {

		System.out.println("Checking for notepad comments...");

		List<Integer> list = prepareDomainPositionListForObservation();

		// Check domains one by one
		for (int i : list) {

			clickDomainAnchorsByPosition(i);

			// get list of comment text areas for the domain
			List<WebElement> listOfCommentTextArea = classRoomObservationFormPageUi
					.getCommentTextAreaListByPlaceHolder(getPropertyValue("observation.form.page.teacher.direct.comment.placeholder"));

			assertThat("Assertion Error : No text area found for comment..",
					listOfCommentTextArea.size(), greaterThan(0));

			for (WebElement element : listOfCommentTextArea) {
				assertThat(
						"Assertion Error : Notepad comments not reflected in the text area..",
						element.getText(),
						containsString(getPropertyValue("observation.form.page.teacher.notepad.comment")));
			}
		}

		System.out
				.println("Notepad comments are reflected in the comment text areas..");
	}

	// To-do : Flash automation..need to wrk on this
	public void addArtifacts() {
		classRoomObservationFormPageUi.getArtifactButton().click();
		classRoomObservationFormPageUi.getChooseFileTypeButton().click();
		classRoomObservationFormPageUi.clickDocumentArtifactTypeButton();
		System.out.println();
	}

	/**
	 * Method to share the draft.
	 */
	public void shareDraftToPrincipal() {

		System.out.println("Sharing draft to principal....");

		classRoomObservationFormPageUi.getShareDraftButton().click();

		WebElement userEmailTextBox = classRoomObservationFormPageUi
				.getShareDraftEmailTxtBox();
		userEmailTextBox
				.sendKeys(getPropertyValue("observation.form.page.principal.email"));

		WebElement emailMsgTextBox = classRoomObservationFormPageUi
				.getShareDraftMsgTxtBox();
		emailMsgTextBox
				.sendKeys(getPropertyValue("observation.form.page.teacher.email.msg"));

		classRoomObservationFormPageUi.getSendDraftButton().click();

		// Wait for div containing message 'Draft sent successfully' to appear
		waitForDivMessageToFadeOut();

		System.out.println("Draft sent successfully");
	}

	/**
	 * THis method will wait for message to become visible and then fade out in
	 * <div>. This message appear when we post comment directly and when draft
	 * is sent
	 */
	private void waitForDivMessageToFadeOut() {

		System.out.println("Inside waitForDivMessageToFadeOut()..");

		WebElement e = classRoomObservationFormPageUi
				.waitForDivMessageToAppear();

		while (e.isDisplayed()) {
			System.out
					.println("inside while..Waiting for message in <div> to fade out");
		}
	}
}
