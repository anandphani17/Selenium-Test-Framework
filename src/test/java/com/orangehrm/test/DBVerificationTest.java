package com.orangehrm.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class DBVerificationTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());

	}

	@Test(dataProvider ="emplVerification", dataProviderClass = DataProviders.class)
	public void verifyEmployeeNameVerificationFromDB(String emp_id, String emp_name) {
		
		SoftAssert softAssert =  getSoftAssert();
		
		
		ExtentManager.logStep("Login with Admin Credentails");
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

		ExtentManager.logStep("Click On PIM Tab");
		homePage.clickOnPimTab();

		ExtentManager.logStep("Search for employee");
		homePage.employeeSearch(emp_name);

		ExtentManager.logStep("Get the Employee name form DB");
		String employee_id = emp_id;

		// Fetch the data into a map

		Map<String, String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);

		String empFirstName = employeeDetails.get("firstName");
		String empMiddleName = employeeDetails.get("middleName");
		String empLastname = employeeDetails.get("lastName");

		String empFirstnameAndMiddleName = (empFirstName + " " + empMiddleName).trim();
		ExtentManager.logStep("Verify the employee First and middle name");
		softAssert.assertTrue(homePage.verifyEmpFirstAndMiddlename(empFirstnameAndMiddleName),
				"First and Middle name are Not matching");

		ExtentManager.logStep("Verify Emp lastName");
		softAssert.assertTrue(homePage.verifyLastName(empLastname));

		ExtentManager.logStep("DB Verification completed");
		softAssert.assertAll();
	}

}
