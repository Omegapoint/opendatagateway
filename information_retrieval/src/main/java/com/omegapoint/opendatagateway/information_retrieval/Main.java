package com.omegapoint.opendatagateway.information_retrieval;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {

  public static void main(String[] args) {
    Main main = new Main();
    try {
      main.readData(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readData(boolean test) throws Exception {

    if (!test) {
      URI uri = new URI("http://vattenweb.smhi.se/vatmarker/rest/download/V%C3%A5tmarker.xls");

      InformationRetrievalWorker worker = new InformationRetrievalWorker(uri);
      worker.call();
    } else {
      ClassLoader classLoader = getClass().getClassLoader();
      try {
        File input = new File(classLoader.getResource("test_input.xls").getFile());
        String data = XlsToCsv.xlsx(input);
        List<Map<?, ?>> nodes = CsvToJsonConverter.readObjectsFromCsv(data);
        //node = CsvToJsonConverter.toJson(reader);
        System.out.println(nodes.size());
        Iterator<Map<?, ?>> i = nodes.iterator();
        while (i.hasNext()) {
          ObjectMapper mapper = new ObjectMapper();
          String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(i.next());
          System.out.println(pretty);
          Publisher.publish("test", pretty);
        }
      } catch (IOException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }
  }
}
