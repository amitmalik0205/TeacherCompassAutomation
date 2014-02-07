package com.qait.tcautomation.pages;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.testng.Assert;
import org.testng.Reporter;

import com.qait.tcautomation.util.PropertyReaderUtil;
import com.qait.tcautomation.util.WaitUtil;

/**
 * This page is corresponding to SLO home page
 * 
 * @author amitmalik
 * 
 */
public class StudentLearningObjectivesPage extends BasePage {

	public StudentLearningObjectivesPage(WebDriver driver,
			DesiredCapabilities capabilities, HttpCommandExecutor executor,
			PropertyReaderUtil propertReaderUtil) {
		super(driver, capabilities, executor, propertReaderUtil);
	}

	public StudentLearningObjectivesPage() {
		super();
	}

	/**
	 * Method will load the page containing SLO form
	 * 
	 * @return ClassRoomObservationFormPage object if page loaded successfully
	 *         otherwise time out exception will be thrown by selenium after
	 *         default seconds
	 */
	public ClassRoomObservationFormPage loadClassroomObservationFormPage() {

		studentLearningObjectivesUi.getCreateNewSloButton().click();

		waitForPageLoad(getPropertyValue("observation.form.page.title1"));

		List<WebElement> list = studentLearningObjectivesUi
				.getSloTableRowsList();
		Assert.assertNotNull(list, "list is null");

		return new ClassRoomObservationFormPage(driver, capabilities, executor,
				propertReaderUtil);
	}

	/**
	 * Method will click on the 'edit' link of the drop down list under the
	 * 'Actions' column of the grid.
	 * 
	 * @param gridRowNo
	 *            Row number of the grid
	 * @return ClassRoomObservationFormPage
	 */
	public ClassRoomObservationFormPage editSloFormGrid(int gridRowNo) {

		studentLearningObjectivesUi.getActionElementFromGrid(gridRowNo).click();

		studentLearningObjectivesUi.getActionListElementFromGrid(gridRowNo, 1)
				.click();

		waitForPageLoad(getPropertyValue("observation.form.page.title1"));

		Reporter.log("Slo edited successfully");
		
		return new ClassRoomObservationFormPage(driver, capabilities, executor,
				propertReaderUtil);
	}

	/**
	 * Method to check for 'slo.status.after.principal.share.draft' status for
	 * slo
	 * 
	 * @param gridRowNo
	 *            Row number of the grid
	 * @return true if status is 'slo.status.after.principal.share.draft'
	 *         otherwise false
	 */
	public boolean isSloInProgress(int gridRowNo) {

		return checkSloStatus(gridRowNo,
				getPropertyValue("slo.status.after.principal.share.draft"));
	}
	
	/**
	 * Method to check for 'slo.status.after.teacher.share.draft' status for
	 * slo
	 * 
	 * @param gridRowNo
	 *            Row number of the grid
	 * @return true if status is 'slo.status.after.teacher.share.draft'
	 *         otherwise false
	 */
	public boolean isSloInDraft(int gridRowNo) {

		return checkSloStatus(gridRowNo,
				getPropertyValue("slo.status.after.teacher.share.draft"));
	}

	/**
	 * Method to check the status of the slo.
	 * 
	 * @param gridRowNo
	 *            Row number of the grid
	 * @param status
	 *            Status to check
	 * @return true if @param status matches with value in 'Status' column of
	 *         the grid otherwise false
	 */
	private boolean checkSloStatus(int gridRowNo, String status) {

		WebElement e = studentLearningObjectivesUi
				.getStatusElementFromGrid(gridRowNo);

		return status.equals(e.getText());
	}
	
	
	/**
	 * Method will select the slo window from the drop down list
	 */
	public void loadSloWindow() {

		studentLearningObjectivesUi.getSloWindowLink().click();

		studentLearningObjectivesUi.waitForSloWindowListVisibility();

		int pos = findPositionOfWindowInList();
		assertThat("Slo window not found in the list", pos,
				Matchers.greaterThan(0));

		WebElement e1 = studentLearningObjectivesUi.getLiElementFromSloWindowList(pos);
		WaitUtil.explicitWait(5);
		e1.click();
		
		WaitUtil.explicitWait(5);
	}
	
	/**
	 * Find position of slo window in drop down list. Window name is specified
	 * as 'slo.window' property
	 * 
	 * @return position of slo window in drop down list
	 */
	public int findPositionOfWindowInList() {
		int pos = 0;

		List<WebElement> list = studentLearningObjectivesUi
				.getSloWindowDropDownSpanList();

		for (WebElement e : list) {
			pos++;
			if (e.getText().equals(getPropertyValue("slo.window"))) {
				break;
			}
		}
		return pos;
	}
	
}
