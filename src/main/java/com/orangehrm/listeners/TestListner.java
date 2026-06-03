package com.orangehrm.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;

public class TestListner implements ITestListener, IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
			}

	// Triggerd when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// Start logging in Extent report
		ExtentManager.startTest(testName);
		ExtentManager.logStep("🚀 TEST STARTED : " + testName);
	}

	// Triggered when a Test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();

		if (result.getTestClass().getName().toLowerCase().contains("api")) {

			// API Test - No Screenshot
			ExtentManager.logStepValidationAPI("Test End: " + testName + " --> ✔ Test Passed");

		} else {

			// UI Test - Screenshot
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!",
					"Test End: " + testName + " --> ✔ Test Passed");
		}
	}

	// Triggered when test fails
	@Override
	public void onTestFailure(ITestResult result) {

		String testName = result.getMethod().getMethodName();
		String failureMessage = result.getThrowable().getMessage();

		ExtentManager.logStep(failureMessage);

		if (result.getTestClass().getName().toLowerCase().contains("api")) {

			// API Test - No Screenshot
			ExtentManager.logFailureAPI("Test End: " + testName + " --> ✘ Test Failed");

		} else {

			// UI Test - Screenshot
			ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed!",
					"Test End: " + testName + " --> ✘ Test Failed");
		}
	}

	// Triggered when test skips
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("⚠ TEST SKIPPED : " + testName);
	}

	// This will trigger when a suite starts
	@Override
	public void onStart(ITestContext context) {
		// Initilize the Extent reports
		ExtentManager.getReporter();
	}

	// Triggered when the suite ends
	@Override
	public void onFinish(ITestContext context) {
		// Closed the Extent reports.
		ExtentManager.endTest();
	}

}
