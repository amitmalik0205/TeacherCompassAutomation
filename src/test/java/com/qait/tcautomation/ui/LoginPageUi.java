package com.qait.tcautomation.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageUi extends BaseUi {

	@FindBy(id = "ctl02_TextBoxUsername")
	private WebElement userNameTxtBox;

	@FindBy(id = "ctl02_TextBoxPassword")
	private WebElement passwordTxtBox;

	@FindBy(id = "ctl02_ButtonLogin")
	private WebElement loginButton;

	public LoginPageUi(WebDriver driver) {
		super(driver);
	}

	public WebElement getUserNameTxtBox() {
		return userNameTxtBox;
	}

	public WebElement getPasswordTxtBox() {
		return passwordTxtBox;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}
}
