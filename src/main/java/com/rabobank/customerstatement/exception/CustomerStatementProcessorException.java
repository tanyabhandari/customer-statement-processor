package com.rabobank.customerstatement.exception;

/**
 * Custom exception for Customer Statement Processor
 */
public class CustomerStatementProcessorException extends Exception {

  /**
   * Parameterized Constructor
   * @param message Exception message string
   */
  public CustomerStatementProcessorException(String message) {
    super(message);
  }

}
