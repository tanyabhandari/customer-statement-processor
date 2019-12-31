package com.rabobank.customerstatement.converters;

import com.rabobank.customerstatement.model.CustomerStatementRecord;
import com.rabobank.customerstatement.model.CustomerStatementResponse;
import com.rabobank.customerstatement.model.CustomerStatementResponse.CustomerStatementResponseBuilder;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/**
 * Converter for Customer Statement Record to Response
 */
@Component
public class CustomerStatementRecordToResponse {

  /**
   * Function converting from {@link CustomerStatementRecord} to {@link CustomerStatementResponse}
   */
  public static Function<CustomerStatementRecord, CustomerStatementResponse> customerStmntRecordToResp = record -> {
    CustomerStatementResponseBuilder customerStatementResponseBuilder = CustomerStatementResponse
        .builder();
    return customerStatementResponseBuilder.reference(record.getReference())
        .description(record.getDescription()).build();
  };
}
