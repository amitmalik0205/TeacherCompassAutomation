package com.qait.tcautomation.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BaseUi {

	WebDriver driver;

	/**
	 * Notification alert bubble
	 */
	@FindBy(id = "mainBackgroundAlertsBubble")
	private WebElement notificationBubbleDiv;

	/**
	 * Span in the Notification alert bubble which shows no of notifications
	 */
	@FindBy(css = "div[id='mainBackgroundAlertsBubble']>span")
	private WebElement notificationBubbleDivSpan;

	/**
	 * List of <a> tags in notification drop down list
	 */
	@FindBy(css = "div[id='body']>ul>li>div:nth-child(2)>a")
	private List<WebElement> notificationAnchorList;

	public BaseUi(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Method to click on an anchor tag having link text as @param linkText
	 * 
	 * @param linkText
	 *            label of the Anchor
	 */
	public void clickAnchorWithLinkText(String linkText) {
		WebElement link = driver.findElement(By.linkText(linkText));
		link.click();
	}

	public WebElement getNotificationBubbleDivSpan() {
		return notificationBubbleDivSpan;
	}

	public WebElement getNotificationBubbleDiv() {
		return notificationBubbleDiv;
	}

	public List<WebElement> getNotificationAnchorList() {
		return notificationAnchorList;
	}
}
