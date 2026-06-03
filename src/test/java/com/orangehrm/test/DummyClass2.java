package com.orangehrm.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass2 extends BaseClass {
	@Test
	public void dummyTest2() {
		//ExtentManager.startTest("DummyTest2 Test"); --> This has been implemented in test listner
		// String title = driver.getTitle();
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifying the Title");
		assert title.equals("OrangeHRM") : "Test Failed - Title is not matching";

		System.out.println("Test passed - Title is matching");
		ExtentManager.logStep("Validation successful");
	}

}