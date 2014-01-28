package com.qait.tcautomation.pages;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;

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
	public void postCommentsForSlo() {

		List<Integer> list = prepareDomainPositionListForObservation();

		// Score domains one by one
		for (int i : list) {

			String domainAnchorElementText = clickDomainAnchorsByPosition(i);

			postComments(getPropertyValue("observation.form.page.direct.comment.placeholder"));

			postOverallComments();

			classRoomObservationFormPageUi.clickSaveButton();

			/*
			 * Check whether status changes to 'completed' under the domain
			 * <div>. If 'completed' is shown then it is successful.
			 */
			String reflectedScore = classRoomObservationFormPageUi
					.getStatusElemtFromLeftPanel(i).getText();

			assertThat(
					"Assertion Error : Comments for all objectives are not posted...",
					reflectedScore, containsString("completed"));

			System.out
					.println("Comments updated using save button for domain :"
							+ domainAnchorElementText);

		}
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
	 */
	public void postComments(String placeHolder) {

		List<WebElement> listOfCommentTextArea = classRoomObservationFormPageUi.getCommentTextAreaListByPlaceHolder(placeHolder);

		assertThat("Assertion Error : No text area found for comment..",
				listOfCommentTextArea.size(), greaterThan(0));

		for (WebElement element : listOfCommentTextArea) {
			element.sendKeys(getPropertyValue("observation.form.page.direct.comment"));
		}

		System.out.println("Comments are posted...");
	}
	
	
	/**
	 * Method to post overall comments
	 */
	public void postOverallComments() {

		WebElement element = classRoomObservationFormPageUi
				.getOverallCommentTextArea();

		element.sendKeys(getPropertyValue("observation.form.page.overall.comment"));

		System.out.println("Overall Comments are posted...");
	}
}
