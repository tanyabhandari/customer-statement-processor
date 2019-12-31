package com.rabobank.customerstatement.controller;

import static com.rabobank.customerstatement.constants.CustomerStatementConstants.STATEMENT_PROCESS_FAILED;

import com.rabobank.customerstatement.model.CustomerStatementResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * API's for Customer Statement
 */
@RequestMapping(value = "/customerstatement/")
@Api(tags = {"Customer Statement Service"})
public interface ICustomerStatementController {

  /**
   * Customer Statement Service process
   *
   * @param file {@link MultipartFile} Upload file (accepted only in CSV/XML format)
   * @return CustomerStatementResponse
   */
  @PostMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = STATEMENT_PROCESS_FAILED, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE, value = "Statement File Processor", response = CustomerStatementResponse.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Operation Successful "),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
  ResponseEntity<List<CustomerStatementResponse>> processStatement(
      @RequestParam(value = "") MultipartFile file);

}
