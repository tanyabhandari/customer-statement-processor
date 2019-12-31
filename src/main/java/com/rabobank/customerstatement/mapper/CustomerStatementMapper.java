package com.rabobank.customerstatement.mapper;

import static com.rabobank.customerstatement.constants.CustomerStatementConstants.APPLICATION_XML;
import static com.rabobank.customerstatement.constants.CustomerStatementConstants.INVALID_FORMAT;
import static com.rabobank.customerstatement.constants.CustomerStatementConstants.TEXT_CSV;
import static com.rabobank.customerstatement.constants.CustomerStatementConstants.TEXT_XML;

import com.rabobank.customerstatement.exception.CustomerStatementProcessorException;
import com.rabobank.customerstatement.model.CustomerStatementRecords;
import com.rabobank.customerstatement.model.CustomerStatementRecords.CustomerStatementRecordsBuilder;
import com.rabobank.customerstatement.parser.StatementParser;
import java.io.IOException;
import java.util.Objects;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Mapper class responsible validating the file's extension and mapping the file content into java
 * objects
 */
@Component
public class CustomerStatementMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerStatementMapper.class);

  @Autowired
  private StatementParser statementParser;

  /**
   * checks the file extension and maps the file content into java objects accordingly
   *
   * @param file {@link MultipartFile}
   * @return {@link CustomerStatementRecords}
   */
  public CustomerStatementRecords getCustomerStatementRecords(MultipartFile file)
      throws IOException, JAXBException, CustomerStatementProcessorException {
    CustomerStatementRecords customerStatementRecords;
    switch (Objects.requireNonNull(file.getContentType())) {
      case TEXT_CSV:
        CustomerStatementRecordsBuilder customerStatementRecordsBuilder = CustomerStatementRecords
            .builder();
        customerStatementRecords = customerStatementRecordsBuilder
            .record(this.statementParser.parseCSV(file.getInputStream())).build();
        break;
      case APPLICATION_XML:
      case TEXT_XML:
        customerStatementRecords = this.statementParser
            .parseXML(file.getInputStream());
        break;
      default:
        LOGGER.info(INVALID_FORMAT);
        throw new CustomerStatementProcessorException(INVALID_FORMAT);
    }
    return customerStatementRecords;
  }


}
