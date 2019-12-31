package com.rabobank.customerstatement.service;

import com.rabobank.customerstatement.exception.CustomerStatementProcessorException;
import com.rabobank.customerstatement.mapper.CustomerStatementMapper;
import com.rabobank.customerstatement.model.CustomerStatementResponse;
import com.rabobank.customerstatement.parser.StatementParser;
import com.rabobank.customerstatement.validator.CustomerStatementRecordValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

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

  @Autowired
  private CustomerStatementService customerStatementService;

  @Before
  public void setup() throws Exception {
    this.testContextManager = new TestContextManager(getClass());
    this.testContextManager.prepareTestInstance(this);
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  public CustomerStatementServiceTest(String inputFileName, String expectedOutputFileName) {
    this.inputFileName = inputFileName;
    this.expectedOutputFileName = expectedOutputFileName;
  }

  @Parameterized.Parameters
  public static Collection inputOutputFileNames() {
    return Arrays.asList(new Object[][]{
        {"records.xml", "output.json"},
        {"records.xml", "output.json"}
    });
  }

  private MultipartFile loadResource(String inputResource) throws IOException {
    ClassLoader classLoader = CustomerStatementServiceTest.class
        .getClassLoader();
    File file = new File(classLoader.getResource(inputResource).getFile());
    FileInputStream input = new FileInputStream(file);
    return new MockMultipartFile(inputResource,
        file.getName(), URLConnection.getFileNameMap().getContentTypeFor(file.getName()), input);
  }

  @Test
  public void testCustomerStatementService()
      throws IOException, JAXBException, CustomerStatementProcessorException {
    System.out.println("Parameterized Input name is : " + inputFileName);
    System.out.println("Parameterized Output name is : " + expectedOutputFileName);
    List<CustomerStatementResponse> customerStatementResponseList = this.customerStatementService
        .processStatement(loadResource(inputFileName));
  }
}
