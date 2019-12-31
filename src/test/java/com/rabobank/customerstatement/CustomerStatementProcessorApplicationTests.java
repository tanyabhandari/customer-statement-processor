package com.rabobank.customerstatement;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles(value = "test")
@TestPropertySource(locations = "classpath:application-test.properties")
class CustomerStatementProcessorApplicationTests {

	@Test
	void contextLoads() {
	}

}
