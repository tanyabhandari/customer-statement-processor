package com.rabobank.customerstatement.parser;

import com.rabobank.customerstatement.converters.CsvToCustomerStatementRecord;
import com.rabobank.customerstatement.model.CustomerStatementRecord;
import com.rabobank.customerstatement.model.CustomerStatementRecords;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

/**
 * Parser for parsing the file content into java objects
 */
@Component
public class StatementParser {

  /**
   * Parses the XML file into CustomerStatementRecords object using JAXB
   *
   * @param inputStream {@link InputStream}
   * @return List {@link CustomerStatementRecord}
   * @throws JAXBException
   */
  public CustomerStatementRecords parseXML(InputStream inputStream) throws JAXBException {
    Unmarshaller unmarshaller = JAXBContext.newInstance(CustomerStatementRecords.class)
        .createUnmarshaller();
    return (CustomerStatementRecords) unmarshaller.unmarshal(inputStream);
  }

  /**
   * Parses the CSV file into CustomerStatementRecord object
   *
   * @param inputStream {@link InputStream}
   * @return List {@link CustomerStatementRecord}
   */
  public List<CustomerStatementRecord> parseCSV(InputStream inputStream) {
    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    return br.lines().skip(1).map(CsvToCustomerStatementRecord.csvToCustStatementRecord)
        .collect(Collectors.toList());
  }
}
