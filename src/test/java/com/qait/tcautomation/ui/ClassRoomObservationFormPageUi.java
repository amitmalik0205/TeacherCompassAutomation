package com.qait.tcautomation.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qait.tcautomation.util.WaitUtil;

/**
 * @author amitmalik
 * 
 */
public class ClassRoomObservationFormPageUi extends BaseUi {

	/*
	 * List will contain all the <li> elements which are in the left pane i.e
	 * Note pad link, Artifacts link, all domains.
	 */
	@FindBy(css = "div[id='leftpanelnew']>ul>li")
	private List<WebElement> liElementList;

	// text area for overall comments
	@FindBy(id = "overallComments")
	private WebElement overallCommentTextArea;

	// text area for note pad comments
	@FindBy(id = "commenttext")
	private WebElement notepadCommentTextArea;

	// Add artifacts button
	@FindBy(css = "a[class='btn btn-small addArtifactLink']")
	private WebElement artifactButton;

	// email of the recipient in share draft model
	@FindBy(id = "associatedUser")
	private WebElement shareDraftEmailTxtBox;

	// email body in share draft model
	@FindBy(id = "sendDraftMessage")
	private WebElement shareDraftMsgTxtBox;

	// Button to launch share draft pop up
	@FindBy(id = "sendDraft")
	private WebElement shareDraftButton;

	// Button to share(send) draft on the pop up
	@FindBy(id = "sendEvaluationDraft")
	private WebElement sendDraftButton;

	@FindBy(css = "button[id='btnText']")
	private WebElement chooseFileTypeButton;

	/**
	 * Share button for principal
	 */
	@FindBy(linkText = "Share")
	private WebElement principalShareButton;

	/**
	 * This button appears on the modal pop up when principal click on
	 * 'principalShareButton' button
	 */
	@FindBy(id = "sendResponseInModal")
	private WebElement finalPrincipalShareButton;

	/**
	 * This <div> contains the pop up containing final send and cancel buttons
	 */
	@FindBy(id = "confirm-send-response-modal")
	private WebElement principalShareDraftModalDiv;

	/**
	 * List if <div> which contains the feedback of principal when draft is
	 * shared by the principal to the teacher. Principal feedback will be
	 * visible in these <div> to teacher
	 */
	@FindBy(css = "td.indicator>div:nth-of-type(2)>div.well>div:nth-of-type(2)")
	private List<WebElement> principalFeedBackDivList;

	@FindBy(id = "approveEvaluation")
	private WebElement approveEvaluationButton;
	
	/**
	 * 'Approve' button on modal pop up(Appears after clicking
	 * approveEvaluationButton)
	 */
	@FindBy(id = "approveSloEvaluation")
	private WebElement finalApproveEvaluationButton;
	
	public ClassRoomObservationFormPageUi(WebDriver driver) {
		super(driver);
	}

	public List<WebElement> getLiElementList() {
		return liElementList;
	}

	public WebElement getOverallCommentTextArea() {
		return overallCommentTextArea;
	}

	public WebElement getNotepadCommentTextArea() {
		return notepadCommentTextArea;
	}

	public WebElement getArtifactButton() {
		return artifactButton;
	}

	public WebElement getShareDraftEmailTxtBox() {
		return shareDraftEmailTxtBox;
	}

	public WebElement getShareDraftMsgTxtBox() {
		return shareDraftMsgTxtBox;
	}

	public WebElement getShareDraftButton() {
		return shareDraftButton;
	}

	public WebElement getSendDraftButton() {
		return sendDraftButton;
	}

	public WebElement getChooseFileTypeButton() {
		return chooseFileTypeButton;
	}

	public WebElement getPrincipalShareButton() {
		return principalShareButton;
	}

	public WebElement getFinalPrincipalShareButton() {
		return finalPrincipalShareButton;
	}

	public WebElement getPrincipalShareDraftModalDiv() {
		return principalShareDraftModalDiv;
	}

	public List<WebElement> getPrincipalFeedBackDivList() {
		return principalFeedBackDivList;
	}
	
	public WebElement getApproveEvaluationButton() {
		return approveEvaluationButton;
	}

	public WebElement getFinalApproveEvaluationButton() {
		return finalApproveEvaluationButton;
	}

	/**
	 * Method returns the <a> element of Domains from left panel
	 * 
	 * @param pos
	 *            position of element in the list<li>
	 * @return
	 */
	public WebElement getDomainAnchorByPositionFromLeftPanel(int pos) {
		return driver.findElement(By
				.cssSelector("div[id='leftpanelnew']>ul>li:nth-of-type(" + pos
						+ ")>a"));

	}

	/**
	 * Method returns the <a> element link text(domain name) of Domains from
	 * left panel
	 * 
	 * @param pos
	 *            position of element in the list<li>
	 * @return
	 */
	public WebElement getDomainAnchorTextByPositionFromLeftPanel(int pos) {
		return driver.findElement(By
				.cssSelector("div[id='leftpanelnew']>ul>li:nth-of-type(" + pos
						+ ")>a>p"));
	}

	/**
	 * Method will return the list of text areas used for comments with place
	 * holder as @param placeHolder
	 * 
	 * @param placeHolder
	 *            place holder for the text area
	 * @return List<WebElement>
	 */
	public List<WebElement> getCommentTextAreaListByPlaceHolder(
			String placeHolder) {

		List<WebElement> listOfCommentTextArea = WaitUtil
				.waitForAllElementsPresence(
						driver,
						By.cssSelector("textarea[placeholder='" + placeHolder
								+ "']"), WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

		return listOfCommentTextArea;
	}

	/**
	 * Method returns the <div> element (which contains the status of the
	 * comments) of Domains from left panel
	 * 
	 * @param pos
	 *            position of element in the list<li>
	 * @return
	 */
	public WebElement getStatusElemtFromLeftPanel(int pos) {
		return driver.findElement(By
				.cssSelector("div[id='leftpanelnew']>ul>li:nth-of-type(" + pos
						+ ")>a>div"));
	}

	/**
	 * Click on the save button
	 */
	public void clickSaveButton() {
		clickAnchorWithLinkText("Save");
	}

	/**
	 * Click on <a> element with 'Notepad' as link text
	 */
	public void clickNotePadButton() {
		clickAnchorWithLinkText("Notepad");
	}

	/**
	 * Click on 'Link to standards' button
	 */
	public void clickLinkToStandardsButton() {
		clickAnchorWithLinkText("Link to standards");
	}

	/**
	 * Wait for all check box elements in the pop up (appears after clicking
	 * 'link to standards' button) and returns their list
	 * 
	 * @return List<WebElement>
	 */
	public List<WebElement> getStandardLinkCheckboxList() {

		List<WebElement> popUpTable = WaitUtil
				.waitForAllElementsPresence(
						driver,
						By.cssSelector("div[id='viewShareData']>div:nth-of-type(2)>div>div>table>tbody>tr>td:nth-of-type(2)>input"),
						WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

		return popUpTable;
	}

	/**
	 * Method to click on the save button of the pop up (appears after clicking
	 * 'link to standards' button)
	 */
	public void clickSaveButtonOfPopUp() {
		WebElement element = WaitUtil
				.waitForElementPresence(
						driver,
						By.cssSelector("div[id='viewShareData']>div:nth-of-type(3)>a:nth-of-type(1)"),
						WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

		element.click();
	}

	/**
	 * This method will wait until message 'Your changes have been saved
	 * successfully' is shown in
	 * <p>
	 * and returns it
	 */
	public WebElement waitForParagraphMessageToAppear() {
		WebElement e = WaitUtil.waitForElementVisiblity(driver,
				By.cssSelector("p[class='alert alert-success']"),
				WaitUtil.DEFAULT_WAIT_FOR_PAGE);

		return e;
	}

	/**
	 * This method will wait until message 'Draft sent successfully' or 'Your
	 * changes have been saved successfully' or 'You can now score this
	 * evaluation. ' is shown in <div> and returns it
	 */
	public WebElement waitForDivMessageToAppear() {

		WebElement e = WaitUtil.waitForElementVisiblity(driver,
				By.cssSelector("div[class='alert alert-success']"),
				WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

		return e;
	}

	/**
	 * Method waits until form in Modal pop up for sending draft becomes visible
	 */
	public void waitForSendDraftFormToVisible() {

		WaitUtil.waitForElementPresence(driver, By.id("senddraftform"),
				WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

		WaitUtil.waitForElementPresence(driver, By.id("associatedUser"),
				WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);
	}
	
	/**
	 * Method waits until Modal pop up for approve SLO becomes visible
	 */
	public void waitForApproveSloModalToVisible() {

		WaitUtil.waitForElementVisiblity(driver, By.id("confirm-approveslo-modal"),
				WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

	}
	
	
	/**
	 * This method will return the column corresponding to @param colNum from
	 * the score table. This score is given after slo is approved.
	 * 
	 * @param colNum
	 *            column number in score row.
	 * @return WebElement
	 */
	public WebElement getScoreColumnForSlo(int colNum) {
		return driver
				.findElement(By
						.cssSelector("table.scoringLevelItems>tbody>tr:nth-child(1)>td:nth-child("
								+ colNum + ")>div:nth-of-type(2)"));
	}
	
/*	public void clickDocumentArtifactTypeButton() {
		// ((JavascriptExecutor) driver).executeScript("checkFileType(11);");
		driver.findElement(
				By.cssSelector("ul[class='dropdown-menu']>li:nth-child(1)>a"))
				.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WebElement e = driver.findElement(By.id("kaltura-uploader"));
		e.click();
		System.out.println();

		((JavascriptExecutor) driver)
				.executeScript("var o = swfobject.getObjectById('kaltura-uploader'); alert(o);");
		System.out.println();
		
		 * WaitUtil.waitForElementPresence(driver,
		 * By.cssSelector("div[id='kaltura-upload-form'] [class='']"),
		 * WaitUtil.DEFAULT_WAIT_FOR_ELEMENT); System.out.println();
		 
	}*/
}
