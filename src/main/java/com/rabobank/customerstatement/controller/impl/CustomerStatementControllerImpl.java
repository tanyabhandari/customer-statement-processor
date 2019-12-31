package com.rabobank.customerstatement.controller.impl;


import static com.rabobank.customerstatement.constants.CustomerStatementConstants.INVALID_FORMAT;
import static com.rabobank.customerstatement.constants.CustomerStatementConstants.LOG_STATEMENT_PROCESS_FAILED;

import com.rabobank.customerstatement.controller.ICustomerStatementController;
import com.rabobank.customerstatement.exception.CustomerStatementProcessorException;
import com.rabobank.customerstatement.model.CustomerStatementResponse;
import com.rabobank.customerstatement.service.CustomerStatementService;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * Rest Controller for Customer Statement Processor Application
 */
@RestController
public class CustomerStatementControllerImpl implements ICustomerStatementController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(CustomerStatementControllerImpl.class);

  @Autowired
  private CustomerStatementService customerStatementService;

  /**
   * Implementation for Customer Statement Process
   *
   * @param file {@link MultipartFile} Upload file (accepted only in CSV/XML format)
   * @return List {@link CustomerStatementResponse}
   */
  @Override
  public ResponseEntity<List<CustomerStatementResponse>> processStatement(MultipartFile file) {
    try {
      List<CustomerStatementResponse> custStatementResponseList = this.customerStatementService
          .processStatement(file);
      return new ResponseEntity<>(custStatementResponseList, HttpStatus.OK);
    } catch (JAXBException | IOException exception) {
      LOGGER.error(LOG_STATEMENT_PROCESS_FAILED + exception.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (CustomerStatementProcessorException exception) {
      LOGGER.error(INVALID_FORMAT + exception.getMessage());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
