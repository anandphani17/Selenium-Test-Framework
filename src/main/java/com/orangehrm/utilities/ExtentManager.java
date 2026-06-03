package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();

	// Initialize the Extent report

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtentReport/ExtentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automition Test Report");
			spark.config().setDocumentTitle("OrangeHRM Report");
			spark.config().setTheme(Theme.DARK);

			// Main report engine
			extent = new ExtentReports(); // Why we are creating this object here
			// Connect spark reporter with extent report
			extent.attachReporter(spark);
			// Adding system information
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
		}
		return extent;

	}

	// Start the test
	public synchronized static ExtentTest startTest(String testName) { // Why we are passing String test name here
		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}

	// End a Test
	public synchronized static void endTest() {
		getReporter().flush();
	}

	// Get Current Threads test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}

	// Method to get the name of the current test
	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if (currentTest != null) {
			return currentTest.getModel().getName();
		} else {
			return "No test is currently active for this thread";
		}
	}

	// Log a step
	public static void logStep(String LogMessage) {
		getTest().info(LogMessage);
	}

	// Log a step validation with screenshot
	public static void logStepWithScreenshot(WebDriver driver, String LogMessage, String screenShotMessage) {
		getTest().pass(LogMessage);
		// Screenshot method
		attachScreenshot(driver, screenShotMessage);
	}
	//Log a step validation for API
	public static void logStepValidationAPI(String LogMessage) {
		getTest().pass(LogMessage);
		
	}

	// Log a failure
	// WebDriver driver, String LogMessage, String screenShotMessage --> Why we are
	// passing these how to know that we have to pass these
	public static void logFailure(WebDriver driver, String LogMessage, String screenShotMessage) {
		String colorMessage = String.format("<span style='color:red;'>%s</span>", LogMessage);
		getTest().fail(colorMessage);
		// Screenshot method
		attachScreenshot(driver, screenShotMessage);	

	}
	
	//Log failur for API
	public static void logFailureAPI(String LogMessage) {
		String colorMessage = String.format("<span style='color:red;'>%s</span>", LogMessage);
		getTest().fail(colorMessage);
		
	}

	// Log a skip
	public static void logSkip(String LogMessage) {
		String colorMessage = String.format("<span style='color:orange;'>%s</span>", LogMessage);
		getTest().skip(colorMessage);
	}

	// Take a screenshot with date and time in the file
	public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		// Formate date and time for file name
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

		// Saving a screenshot to a file

		String destPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/" + screenshotName + "_"
				+ timeStamp + ".png";
		File finalPath = new File(destPath);
		try {
			FileUtils.copyFile(src, finalPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Convert screenshot into Base64 for embedding in the report
		String base64Format = convertToBase64(src);
		return base64Format;
	}

	// Convert screenshot to base64 format
	public static String convertToBase64(File screenShotFile) {
		String base64Format = "";
		// Read the file content into byte array
		byte[] fileContent;
		try {
			fileContent = FileUtils.readFileToByteArray(screenShotFile);
			base64Format = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Format;
	}

	// Attach screenshot to report using base64
	// Why we are adding synchronized word i dont know explain
	public synchronized static void attachScreenshot(WebDriver driver, String message) {
		try {
			String screenShotBase64 = takeScreenshot(driver, getTestName());
			getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder
					.createScreenCaptureFromBase64String(screenShotBase64).build());
		} catch (Exception e) {
			getTest().fail("Failed to attach screenshot" + message);
			e.printStackTrace();
		}
	}

	// Register webdriver for current thread
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}

	

	

}
