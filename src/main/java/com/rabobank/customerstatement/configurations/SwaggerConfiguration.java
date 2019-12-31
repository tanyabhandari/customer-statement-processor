package com.rabobank.customerstatement.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration class
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Value("${api.ver}")
  String apiVersion;

  @Value("${api.description}")
  String apiDescription;

  @Value("${api.title}")
  String apiTitle;

  @Value("${swagger.config.api.basepackage}")
  String basePackage;

  @Value("${swagger.config.api.path}")
  String swaggerPath;

  /**
   *
   * @return
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage(basePackage))
        .paths(PathSelectors.regex(swaggerPath)).build().apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder().title(apiTitle).description(apiDescription)
        .license("").version(apiVersion)
        .build();
  }
}