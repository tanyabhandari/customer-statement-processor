package com.rabobank.customerstatement.converters;

import com.rabobank.customerstatement.model.CustomerStatementRecord;
import com.rabobank.customerstatement.model.CustomerStatementRecord.CustomerStatementRecordBuilder;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/**
 * Converter functional interface
 */
@Component
public class CsvToCustomerStatementRecord {

  /**
   * Function converting from String to {@link CustomerStatementRecord}
   */
  public static Function<String, CustomerStatementRecord> csvToCustStatementRecord = (line) -> {
    String[] record = line.split(",");// This can be delimiter which
    CustomerStatementRecordBuilder customerStatementRecordBuilder = CustomerStatementRecord
        .builder();
    if (record[0] != null && record[0].trim().length() > 0) {
      customerStatementRecordBuilder.reference(Long.valueOf(record[0]));
    }
    if (record[1] != null && record[1].trim().length() > 0) {
      customerStatementRecordBuilder.accountNumber(record[1]);
    }
    if (record[2] != null && record[2].trim().length() > 0) {
      customerStatementRecordBuilder.description(record[2]);
    }
    if (record[3] != null && record[3].trim().length() > 0) {
      customerStatementRecordBuilder.startBalance(Double.valueOf(record[3]));
    }
    if (record[4] != null && record[4].trim().length() > 0) {
      customerStatementRecordBuilder.mutation(Double.valueOf(record[4]));
    }
    if (record[5] != null && record[5].trim().length() > 0) {
      customerStatementRecordBuilder.endBalance(Double.valueOf(record[5]));
    }
    return customerStatementRecordBuilder.build();
  };
}
