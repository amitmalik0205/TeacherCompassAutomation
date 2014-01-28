package com.qait.tcautomation.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qait.tcautomation.util.WaitUtil;

public class ClassRoomObservationFormPageUi extends BaseUi {

	/*
	 * List will contain all the <li> elements which are in the left pane i.e
	 * Note pad link, Artifacts link, all domains.
	 */
	@FindBy(xpath = "//div[@id='leftpanelnew']/ul/li")
	private List<WebElement> liElementList;

	//text area for overall comments
	@FindBy(id = "overallComments")
	private WebElement overallCommentTextArea;

	public ClassRoomObservationFormPageUi(WebDriver driver) {
		super(driver);
	}

	public List<WebElement> getLiElementList() {
		return liElementList;
	}

	public WebElement getOverallCommentTextArea() {
		return overallCommentTextArea;
	}

	/**
	 * Method returns the <a> element of Domains from left panel
	 * 
	 * @param pos
	 *            position of element in the list<li>
	 * @return
	 */
	public WebElement getDomainAnchorByPositionFromLeftPanel(int pos) {
		return driver.findElement(By.xpath("//div[@id='leftpanelnew']/ul/li["
				+ pos + "]/a"));
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
		return driver.findElement(By.xpath("//div[@id='leftpanelnew']/ul/li["
				+ pos + "]/a/p"));
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
						By.xpath("//textarea[@placeholder='" + placeHolder
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
		return driver.findElement(By.xpath("//div[@id='leftpanelnew']/ul/li["
				+ pos + "]/a/div"));
	}
	
	public void clickSaveButton() {
		clickAnchorWithLinkText("Save");
	}
}
