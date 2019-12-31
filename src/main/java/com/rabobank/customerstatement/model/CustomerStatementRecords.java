package com.rabobank.customerstatement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data model describing CustomerStatementRecords object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(builderClassName = "CustomerStatementRecordsBuilder")
@JsonDeserialize(builder = CustomerStatementRecords.CustomerStatementRecordsBuilder.class)
@XmlRootElement(name = "records")
public class CustomerStatementRecords {

  @XmlElement(name = "record")
  private List<CustomerStatementRecord> record;

  /**
   * Builder pattern, used to provide immutability through application layers.
   */
  @JsonPOJOBuilder(withPrefix = "")
  public static class CustomerStatementRecordsBuilder {
    // Lombok will add constructor, setters, build method
    @XmlElement(name = "record")
    private List<CustomerStatementRecord> record;

  }
}
