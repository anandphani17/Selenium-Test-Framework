package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {

	private ActionDriver actionDriver;
	
	//Define locators using by class
	
	private By adminText = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[normalize-space()='Logout']");
	private By orangeHRMLogo = By.xpath("//img[@alt='client brand banner']");
	
	private By pimTab = By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][normalize-space()='PIM']");
	private By employeeSearch = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
	private By searchButton = By.xpath("//button[normalize-space()='Search']");
	private By empFirstAndMiddleName = By.xpath("//div[contains(text(),'Anand')]");
	private By emplastName = By.xpath("//div[contains(text(),'phani')]");
	
	
	// to initilize the action driver object by passing webdriver instance

	/*	public HomePage(WebDriver driver) {
			this.actionDriver = new ActionDriver(driver);
		}*/
		public HomePage(WebDriver driver) {
			this.actionDriver = BaseClass.getActionDriver();
		}
	
		//Method to verify if admin is visable
		
		public boolean isAdminTextVisable() {
			return actionDriver.isDisplayed(adminText);
		}
		//Method to navigate to PIM tab
		public void clickOnPimTab() {
			actionDriver.click(pimTab);
		}
		//emp search
		public void employeeSearch(String value) {
			actionDriver.enterText(employeeSearch, value);
			actionDriver.click(searchButton);
			actionDriver.scrollToElement(empFirstAndMiddleName);
		}
		
		//verify employee first and middle name
		public boolean verifyEmpFirstAndMiddlename(String empFirstnameAndMiddleNameFromDB) {
			return actionDriver.compareText(empFirstAndMiddleName, empFirstnameAndMiddleNameFromDB);
		}
		
		public boolean verifyLastName(String emplastNameFromDB) {
			return actionDriver.compareText(emplastName, emplastNameFromDB);
		}
		
		
		public boolean verifyOrangeHRMLogo() {
			return actionDriver.isDisplayed(orangeHRMLogo);
		}
		//Method to perform logout operation
		public void logout() {
			actionDriver.click(userIDButton);
			actionDriver.click(logoutButton);
		}
		
		
}
