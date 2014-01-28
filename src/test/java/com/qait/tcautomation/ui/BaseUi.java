package com.qait.tcautomation.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BaseUi {

	WebDriver driver;
	
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
		WebElement link = driver.findElement(By.xpath("(//a[contains(text(),'"
				+ linkText + "')])"));
		link.click();
	}
}
