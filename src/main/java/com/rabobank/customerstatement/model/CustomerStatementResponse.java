package com.rabobank.customerstatement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data model describing CustomerStatementResponse object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(builderClassName = "CustomerStatementResponseBuilder")
@JsonDeserialize(builder = CustomerStatementResponse.CustomerStatementResponseBuilder.class)
public class CustomerStatementResponse {

  @ApiModelProperty(value = "Reference Id of Customer", dataType = "Long")
  private Long reference;
  @ApiModelProperty(value = "Description of Statement", dataType = "String")
  private String description;

  /**
   * Builder pattern, used to provide immutability through application layers.
   */
  @JsonPOJOBuilder(withPrefix = "")
  public static class CustomerStatementResponseBuilder {
    // Lombok will add constructor, setters, build method

  }

}
