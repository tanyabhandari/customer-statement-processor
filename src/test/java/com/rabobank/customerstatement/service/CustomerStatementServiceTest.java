package com.rabobank.customerstatement.service;

import com.google.gson.Gson;
import com.rabobank.customerstatement.exception.CustomerStatementProcessorException;
import com.rabobank.customerstatement.mapper.CustomerStatementMapper;
import com.rabobank.customerstatement.model.CustomerStatementResponse;
import com.rabobank.customerstatement.parser.StatementParser;
import com.rabobank.customerstatement.utilities.FileRetrieveUtility;
import com.rabobank.customerstatement.validator.CustomerStatementRecordValidator;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test cases for Customer Statement Processor
 */
@RunWith(Parameterized.class)
@ActiveProfiles(value = "test")
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
@ContextConfiguration(classes = {CustomerStatementService.class,
    CustomerStatementRecordValidator.class,
    CustomerStatementMapper.class, StatementParser.class})
public class CustomerStatementServiceTest {

  private String inputFileName;
  private String expectedOutputFileName;

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;
  private TestContextManager testContextManager;
  private FileRetrieveUtility fileRetrieveUtility;

  @Autowired
  private CustomerStatementService customerStatementService;

  /**
   * executes before test methods
   *
   * @throws Exception
   */
  @Before
  public void setup() throws Exception {
    this.testContextManager = new TestContextManager(getClass());
    this.testContextManager.prepareTestInstance(this);
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    this.fileRetrieveUtility = new FileRetrieveUtility();
  }

  /**
   * Parameterized constructor
   *
   * @param inputFileName
   * @param expectedOutputFileName
   */
  public CustomerStatementServiceTest(String inputFileName, String expectedOutputFileName) {
    this.inputFileName = inputFileName;
    this.expectedOutputFileName = expectedOutputFileName;
  }

  /**
   * Paramterized parameters
   *
   * @return Arraylist
   */
  @Parameterized.Parameters
  public static Collection inputOutputFileNames() {
    return Arrays.asList(new Object[][]{
        {"balance-validation-check-records.xml", "balance-validation-output.json"},
        {"duplicate-input-records.csv", "duplicate-output-csv.json"},
        {"duplicate-and-validation-check-records.xml",
            "duplicate-and-validation-output.json"}
    });
  }

  /**
   * Parameterized Test for triggering processor with input file
   *
   * @throws IOException
   * @throws JAXBException
   * @throws CustomerStatementProcessorException
   * @throws JSONException
   */
  @Test
  public void testCustomerStatementService()
      throws IOException, JAXBException, CustomerStatementProcessorException, JSONException {
    System.out.println("Parameterized Input name is : " + inputFileName);
    System.out.println("Parameterized Output name is : " + expectedOutputFileName);
    List<CustomerStatementResponse> customerStatementResponseList = this.customerStatementService
        .processStatement(fileRetrieveUtility.loadInputResource(inputFileName));
    String actualOutput = new Gson().toJson(customerStatementResponseList);
    String expectedOutput = fileRetrieveUtility.loadOutputResource(expectedOutputFileName);
    System.out.println("actualOutput" + actualOutput);
    System.out.println(
        "expectedOutput" + expectedOutput);
    JSONAssert.assertEquals(expectedOutput, actualOutput, JSONCompareMode.STRICT);
  }
}
