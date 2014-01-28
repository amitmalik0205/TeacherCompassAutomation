package com.qait.tcautomation.ui;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StudentLearningObjectivesUi extends BaseUi {

	@FindBy(id="btnCreateNewSLO")
	private WebElement createNewSloButton;
	
	@FindBy(xpath="//div[@id='mainGrid']/table/tbody/tr")
	private List<WebElement> sloTableRowsList;
	
	public StudentLearningObjectivesUi(WebDriver driver) {
		super(driver);
	}

	public WebElement getCreateNewSloButton() {
		return createNewSloButton;
	}

	public List<WebElement> getSloTableRowsList() {
		return sloTableRowsList;
	}
}
