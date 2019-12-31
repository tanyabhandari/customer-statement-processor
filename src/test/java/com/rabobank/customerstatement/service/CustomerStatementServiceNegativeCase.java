package com.rabobank.customerstatement.service;

import com.rabobank.customerstatement.exception.CustomerStatementProcessorException;
import com.rabobank.customerstatement.mapper.CustomerStatementMapper;
import com.rabobank.customerstatement.parser.StatementParser;
import com.rabobank.customerstatement.utilities.FileRetrieveUtility;
import com.rabobank.customerstatement.validator.CustomerStatementRecordValidator;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
@TestPropertySource(locations = "classpath:application-test.properties")@WebAppConfiguration
@ContextConfiguration(classes = {CustomerStatementService.class,
    CustomerStatementRecordValidator.class,
    CustomerStatementMapper.class, StatementParser.class})
public class CustomerStatementServiceNegativeCase {

  @Autowired
  CustomerStatementService customerStatementService;

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;
  private TestContextManager testContextManager;
  private FileRetrieveUtility fileRetrieveUtility;

  @Before
  public void setup() throws Exception {
    this.testContextManager = new TestContextManager(getClass());
    this.testContextManager.prepareTestInstance(this);
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    this.fileRetrieveUtility = new FileRetrieveUtility();
  }

  @Test(expected = CustomerStatementProcessorException.class)
  public void testInvalidFileFormat()
      throws IOException, JAXBException, CustomerStatementProcessorException {
    this.customerStatementService
        .processStatement(fileRetrieveUtility.loadInputResource("records.html"));
  }

}
