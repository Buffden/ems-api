package com.ems.employee_management_system;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeManagementSystemApplicationTests {

	static {
		String dbUser = System.getenv("DB_CREDENTIALS_USR");
		String dbPassword = System.getenv("DB_CREDENTIALS_PSW");

		if (dbUser == null || dbPassword == null) {
			Dotenv dotenv = Dotenv.configure()
					.directory("./")
					.load();
			dbUser = dotenv.get("DB_CREDENTIALS_USR");
			dbPassword = dotenv.get("DB_CREDENTIALS_PSW");
		}

		System.setProperty("DB_CREDENTIALS_USR", dbUser);
		System.setProperty("DB_CREDENTIALS_PSW", dbPassword);
	}

	@Test
	void contextLoads() {
		// Your test logic here
	}
}
