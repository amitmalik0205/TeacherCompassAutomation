package com.qait.tcautomation.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qait.tcautomation.util.WaitUtil;

public class StudentLearningObjectivesUi extends BaseUi {

	@FindBy(id = "btnCreateNewSLO")
	private WebElement createNewSloButton;

	@FindBy(css = "table[class='table table-striped dataview']>tbody>tr")
	private List<WebElement> sloTableRowsList;
	
	/**
	 * Button to select slo window.
	 */
	@FindBy(css = "div#WindowSelector>a")
	private WebElement sloWindowLink;

	/**
	 * List of <span> which contains the name of the windows in drop down list
	 */
	@FindBy(css = "div#WindowSelector>ul>li>span")
	private List<WebElement> sloWindowDropDownSpanList;
	
	public StudentLearningObjectivesUi(WebDriver driver) {
		super(driver);
	}

	public WebElement getCreateNewSloButton() {
		return createNewSloButton;
	}

	public List<WebElement> getSloTableRowsList() {
		return sloTableRowsList;
	}
	
	public WebElement getSloWindowLink() {
		return sloWindowLink;
	}
	
	public List<WebElement> getSloWindowDropDownSpanList() {
		return sloWindowDropDownSpanList;
	}

	/**
	 * Method will return <a> element form from the drop down list which appears
	 * when we click on 'Actions' column(4th column) of the grid
	 * 
	 * @param gridRowNo
	 *            Row number of the grid
	 * @param listRowNo
	 *            Row number of the drop down list. listRowNo = 1 for edit & review and
	 *            listRowNo = 2 for delete
	 * @return
	 */
	public WebElement getActionListElementFromGrid(int gridRowNo, int listRowNo) {
		return driver
				.findElement(By
						.cssSelector("table[class='table table-striped dataview']>tbody>tr:nth-child("
								+ gridRowNo
								+ ")>td:nth-child(4)>div>ul>li:nth-child("
								+ listRowNo + ")"));
	}

	/**
	 * Method will return <a> element corresponding to 'Actions' column(4th
	 * column) of the grid for the row specified by @param gridRowNo
	 * 
	 * @param gridRowNo
	 *            Row number of the grid
	 * @return WebElement
	 */
	public WebElement getActionElementFromGrid(int gridRowNo) {
		return driver
				.findElement(By
						.cssSelector("table[class='table table-striped dataview']>tbody>tr:nth-child("
								+ gridRowNo + ")>td:nth-child(4)>div>a"));
	}

	/**
	 * Method will return web element corresponding to 'Status' column(2nd
	 * column) of the grid for the row specified by @param gridRowNo
	 * 
	 * @param gridRowNo
	 *            Row number of the grid
	 * @return WebElement
	 */
	public WebElement getStatusElementFromGrid(int gridRowNo) {
		return driver
				.findElement(By
						.cssSelector("table[class='table table-striped dataview']>tbody>tr:nth-child("
								+ gridRowNo + ")>td:nth-child(2)"));
	}
	
	
	/**
	 * Method will wait for drop down list to select slo window to become
	 * visible
	 */
	public void waitForSloWindowListVisibility() {

		WebElement e = WaitUtil.waitForElementVisiblity(driver,
				By.cssSelector("div#WindowSelector>ul"),
				WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);
	}
	
	/**
	 * method to get the <li>element from the drop down list which appears by
	 * clicking 'sloWindowLink'
	 * 
	 * @param index
	 *            index of <li>in list
	 * @return WebElement
	 */
	public WebElement getLiElementFromSloWindowList(int index) {
		return driver.findElement(By
				.cssSelector("div#WindowSelector>ul>li:nth-child(" + index
						+ ")"));
	}
}
