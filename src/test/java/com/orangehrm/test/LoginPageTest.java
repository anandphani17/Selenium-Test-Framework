package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		// To create the object of those pages
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}

	@Test(dataProvider ="validLoginData", dataProviderClass = DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {
		//ExtentManager.startTest("Vallid login Test"); --> This has been implemented in test listner
		logger.info("Running testMethod1 on thread: "+ Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login page entering username and password");
		loginPage.login(username, password);
		ExtentManager.logStep("Verifying admin tab is visable of not");
		Assert.assertTrue(homePage.isAdminTextVisable(), "Admin tab should be visable after successful login");
		ExtentManager.logStep("Validation successful");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
		staticWait(2);
	}

	@Test(dataProvider ="inValidLoginData", dataProviderClass = DataProviders.class)
	public void inValidLoginTest(String username, String password) {
		//ExtentManager.startTest("In-Vallid login Test");
		logger.info("Running testMethod2 on thread: "+ Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login page entering username and password");
		loginPage.login(username, password);
		String expectedErrorMessage = "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrorMsg(expectedErrorMessage), "Test Failed - Invalid Error message");
		ExtentManager.logStep("Validation successful");
		ExtentManager.logStep("Logged out successfully");
	}

}
