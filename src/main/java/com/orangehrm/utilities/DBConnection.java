package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.orangehrm.base.BaseClass;

public class DBConnection {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrm";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	private static final Logger logger = BaseClass.logger;

	public static Connection getDBConnection() {

		try {
			logger.info("Starting DB Connection...");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			logger.info("DB connection successful....");
			return conn;
		} catch (SQLException e) {
			logger.error("Error while extablishing the DB connection");
			e.printStackTrace();
			return null;
		}

	}

	// Get the Employee details from DB and store in a map
	public static Map<String, String> getEmployeeDetails(String employee_id) {

		String query = "SELECT emp_firstname,emp_middle_name,emp_lastname FROM `hs_hr_employee` WHERE employee_id ="
				+ employee_id;
		Map<String, String> employeedetails = new HashMap<>();

		try (Connection conn = getDBConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			logger.info("Executing Query " + query);
			if (rs.next()) {
				String firstName = rs.getString("emp_firstname");
				String middleName = rs.getString("emp_middle_name");
				String lastName = rs.getString("emp_lastname");
				// Store in a map
				employeedetails.put("firstName", firstName);
				employeedetails.put("middleName", middleName!=null? middleName:"");
				employeedetails.put("lastName", lastName);

				logger.info("Query Executed Successfully..");
				logger.info("Employee data Fetched "+employeedetails);

			} else {
				logger.error("Employee not Found ");
			}
		} catch (Exception e) {
			logger.error("Error while Executing query");
			e.printStackTrace();
		}
		return employeedetails;
	}
}
