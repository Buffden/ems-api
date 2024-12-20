package com.ems.employee_management_system;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeManagementSystemApplicationTests {

	static {
		Dotenv dotenv = Dotenv.configure()
				.directory("./") // Path to the directory with the .env file
				.load();

		System.setProperty("DB_CREDENTIALS_USR", dotenv.get("DB_CREDENTIALS_USR"));
		System.setProperty("DB_CREDENTIALS_PSW", dotenv.get("DB_CREDENTIALS_PSW"));
	}

	@Test
	void contextLoads() {
		// Your test logic here
	}
}
