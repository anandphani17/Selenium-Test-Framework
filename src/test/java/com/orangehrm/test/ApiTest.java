package com.orangehrm.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.utilities.APIUtility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;

import io.restassured.response.Response;

public class ApiTest {
	@Test
	public void verifyGetUserAPI() {
		
		SoftAssert softAssert =  new SoftAssert();
		
		//Step1 Define API End Point
		String endPoint = "https://jsonplaceholder.typicode.com/users/1";
		ExtentManager.logStep("API EndPoint "+ endPoint);
		
		//Step2;Send GET Request
		ExtentManager.logStep("Sending Get Request to the API");
		Response response = APIUtility.sendGetRequest(endPoint);
		
		//Step 3 Validate status code
		ExtentManager.logStep("Validating API Response status code");
		boolean isStatusCodeValid = APIUtility.validateStatusCode(response, 200);
		
		softAssert.assertTrue(isStatusCodeValid,"Status code is not as expected");
		
		if(isStatusCodeValid) {
			ExtentManager.logStepValidationAPI("Status code Validation passed");
		}
		else {
			ExtentManager.logStepValidationAPI("Status Code Validation failed");
		}
		
		//Step4 Validate username
		ExtentManager.logStep("Validating response body for the username");
		String userName = APIUtility.getJsonValue(response, "username");
		boolean isUserNameValid = "Bret".equals(userName);
		softAssert.assertTrue(isUserNameValid, "UserName not valid");
		if(isUserNameValid) {
			ExtentManager.logStepValidationAPI("Username Validation passed");
		}
		else {
			ExtentManager.logStepValidationAPI("Username Validation failed");
		}
		
		//Step5 Validate EmailID
		ExtentManager.logStep("Validating response body for the email");
		String userEmail = APIUtility.getJsonValue(response, "email");
		boolean isEmailValid = "Sincere@april.biz".equals(userEmail);
		softAssert.assertTrue(isEmailValid, "email not valid");
		if(isEmailValid) {
			ExtentManager.logStepValidationAPI("email Validation passed");
		}
		else {
			ExtentManager.logStepValidationAPI("email Validation failed");
		}
		softAssert.assertAll();
	}
}
