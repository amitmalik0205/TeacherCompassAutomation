package com.qait.tcautomation.pages;

import com.qait.tcautomation.enums.UserType;

public class LoginPage extends BasePage {

	
	public LoginPage() {
		super();
	}
	
	/**
	 * Utility method to perform login functionality
	 * 
	 * @param userType
	 *            : Type of user
	 */
	public HomePage login(UserType userType) {

		String userName;
		String password;

		if (userType == UserType.TEACHER) {
			userName = getPropertyValue("slo.teacher.username");
			password = getPropertyValue("slo.teacher.password");
		} else {
			userName = getPropertyValue("slo.principal.username");
			password = getPropertyValue("slo.principal.password");
		}

		loadPage(getPropertyValue("login.url"), getPropertyValue("login.title"));

		loginPageUi.getUserNameTxtBox().sendKeys(userName);
		loginPageUi.getPasswordTxtBox().sendKeys(password);
		loginPageUi.getLoginButton().click();
		
		waitForPageLoad(getPropertyValue("homepage.title"));
		
		return new HomePage(driver, capabilities, executor, propertReaderUtil);
	}
}
