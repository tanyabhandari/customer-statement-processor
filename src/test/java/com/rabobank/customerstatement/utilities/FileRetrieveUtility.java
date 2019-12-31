package com.rabobank.customerstatement.utilities;

import com.rabobank.customerstatement.service.CustomerStatementServiceTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileRetrieveUtility {

  public MultipartFile loadInputResource(String inputResource) throws IOException {
    ClassLoader classLoader = CustomerStatementServiceTest.class
        .getClassLoader();
    File file = new File(classLoader.getResource(inputResource).getFile());
    FileInputStream input = new FileInputStream(file);
    return new MockMultipartFile(inputResource,
        file.getName(),
        file.getName().contains("csv") ? "text/csv" :
            URLConnection.getFileNameMap().getContentTypeFor(file.getName()), input);
  }

  public String loadOutputResource(String resource) throws IOException {
    try (InputStream is = this.getClass().getClassLoader()
        .getResourceAsStream(resource);
        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
      scanner.useDelimiter("\\A");
      return scanner.hasNext() ? scanner.next() : "";
    }
  }

}
