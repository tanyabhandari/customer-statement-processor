package com.rabobank.customerstatement.service;

import com.rabobank.customerstatement.converters.CustomerStatementRecordToResponse;
import com.rabobank.customerstatement.exception.CustomerStatementProcessorException;
import com.rabobank.customerstatement.model.CustomerStatementRecord;
import com.rabobank.customerstatement.model.CustomerStatementResponse;
import com.rabobank.customerstatement.validator.CustomerStatementRecordValidator;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Customer Statement Service
 */
@Service
public class CustomerStatementService {

  @Autowired
  private CustomerStatementRecordValidator custmrStmntRecordValidator;

  /**
   * Processes statement and returns list of failed records in format {@link
   * CustomerStatementRecord}
   *
   * @param file {@link MultipartFile}
   * @return List {@link CustomerStatementRecord}
   * @throws IOException
   * @throws JAXBException
   * @throws CustomerStatementProcessorException
   */
  public List<CustomerStatementResponse> processStatement(MultipartFile file)
      throws IOException, JAXBException, CustomerStatementProcessorException {
    List<CustomerStatementRecord> failedRecords = this.custmrStmntRecordValidator
        .validateFile(file);
    return failedRecords.stream().
        map(CustomerStatementRecordToResponse.customerStmntRecordToResp)
        .collect(Collectors.toList());
  }


}
