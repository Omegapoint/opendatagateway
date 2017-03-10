package com.omegapoint.opendatagateway.information_retrieval;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class Main {

  public static void main(String[] args) {
    Main main = new Main();
    main.readData();
  }

  private void readData() {
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      File input = new File(classLoader.getResource("test_input.xls").getFile());
      String data = XlsToCsv.xlsx(input);
      System.out.println(data);
      ObjectMapper mapper = new ObjectMapper();
      StringReader reader = new StringReader(data);
      JsonNode node = CsvToJsonConverter.toJson(reader);
      //node = CsvToJsonConverter.toJson(reader);
      String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
      System.out.println(pretty);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
