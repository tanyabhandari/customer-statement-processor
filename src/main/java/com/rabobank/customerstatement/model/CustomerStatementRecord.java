package com.rabobank.customerstatement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data model describing CustomerStatementRecord object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "CustomerStatementRecordBuilder")
@NoArgsConstructor
@JsonDeserialize(builder = CustomerStatementRecord.CustomerStatementRecordBuilder.class)
public class CustomerStatementRecord {

  @XmlAttribute
  private Long reference;
  @XmlElement(name = "accountNumber")
  private String accountNumber;
  @XmlElement(name = "description")
  private String description;
  @XmlElement(name = "startBalance")
  private Double startBalance;
  @XmlElement(name = "mutation")
  private Double mutation;
  @XmlElement(name = "endBalance")
  private Double endBalance;

  /**
   * Builder pattern, used to provide immutability through application layers.
   */
  @JsonPOJOBuilder(withPrefix = "")
  public static class CustomerStatementRecordBuilder {

    // Lombok will add constructor, setters, build method
    @XmlAttribute
    private Long reference;

    @XmlElement(name = "accountNumber")
    private String accountNumber;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "startBalance")
    private Double startBalance;
    @XmlElement(name = "mutation")
    private Double mutation;
    @XmlElement(name = "endBalance")
    private Double endBalance;

  }
}
