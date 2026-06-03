package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {

	private ActionDriver actionDriver;

	// Define locators using by class

	private By userNameField = By.name("username");
	private By passwordField = By.cssSelector("input[type='Password']");
	private By loginBtn = By.xpath("//button[normalize-space()='Login']");
	private By errorMsg = By.xpath("//p[text()='Invalid credentials']");

	// to initilize the action driver object by passing webdriver instance

	/*
	 * public LoginPage(WebDriver driver) { this.actionDriver = new
	 * ActionDriver(driver); }
	 */
	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}

	// Method to perform login

	public void login(String username, String password) {
		actionDriver.enterText(userNameField, username);
		actionDriver.enterText(passwordField, password);
		actionDriver.click(loginBtn);

	}

	// Method to check if error message is displayed
	public boolean isErrorMsgDisplayed() {
		return actionDriver.isDisplayed(errorMsg);
	}

	// Method to get the text from error message
	public String getErrorMsgText() {
		return actionDriver.getText(errorMsg);
	}

	// Verify if error is correct or not
	public boolean verifyErrorMsg(String expectedError) {
		return actionDriver.compareText(errorMsg, expectedError);
	}

}
