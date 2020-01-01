package com.rabobank.customerstatement.validator;

import com.rabobank.customerstatement.exception.CustomerStatementProcessorException;
import com.rabobank.customerstatement.mapper.CustomerStatementMapper;
import com.rabobank.customerstatement.model.CustomerStatementRecord;
import com.rabobank.customerstatement.model.CustomerStatementRecords;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validator class responsible for  collecting the failed records based on duplicate references and
 * wrong mutations
 */
@Component
public class CustomerStatementRecordValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerStatementRecordValidator.class);

  @Autowired
  private CustomerStatementMapper customerStatementMapper;

  /**
   * \
   *
   * @param file {@link MultipartFile}
   * @return List {@link CustomerStatementRecord}
   * @throws CustomerStatementProcessorException
   * @throws JAXBException
   * @throws IOException
   */
  public List<CustomerStatementRecord> validateFile(MultipartFile file)
      throws CustomerStatementProcessorException, JAXBException, IOException {
    CustomerStatementRecords customerStatementRecords = this.customerStatementMapper
        .getCustomerStatementRecords(file);
    return getFailedRecords(customerStatementRecords);
  }


  private List<CustomerStatementRecord> getFailedRecords(CustomerStatementRecords records) {

    List<CustomerStatementRecord> uniqueReference = records.getRecord().stream().
        filter(distinctByKey(CustomerStatementRecord::getReference)).collect(Collectors.toList());
    List<CustomerStatementRecord> duplicateReference = records.getRecord().stream().
        filter(record -> !uniqueReference.contains(record)).collect(Collectors.toList());
    List<CustomerStatementRecord> mutationIssueRecords = uniqueReference.stream()
        .filter(record -> !isValid(record))
        .collect(Collectors.toList());
    List<CustomerStatementRecord> failureRecords = new ArrayList<>();
    failureRecords.addAll(mutationIssueRecords);
    failureRecords.addAll(duplicateReference);
    LOGGER.info("failed records collected");
    return failureRecords;
  }

  private boolean isValid(CustomerStatementRecord customerStatementRecord) {
    return Math.round(
        customerStatementRecord.getEndBalance() - customerStatementRecord.getStartBalance()) == Math
        .round(customerStatementRecord.getMutation());
  }

  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(keyExtractor.apply(t));
  }


}
