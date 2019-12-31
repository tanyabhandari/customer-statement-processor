package com.rabobank.customerstatement.exception;

/**
 * Custom exception for Customer Statement Processor
 */
public class CustomerStatementProcessorException extends Exception {

  public CustomerStatementProcessorException(Throwable excption) {
    super(excption);
  }

  public CustomerStatementProcessorException(String message) {
    super(message);
  }

  public CustomerStatementProcessorException(String message, Throwable cause) {
    super(message, cause);
  }


}
