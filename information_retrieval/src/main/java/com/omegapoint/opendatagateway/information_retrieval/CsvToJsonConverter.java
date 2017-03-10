package com.omegapoint.opendatagateway.information_retrieval;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;


public class CsvToJsonConverter {

  public static JsonNode toJson(Reader reader) throws IOException {
    CsvMapper mapper = new CsvMapper();
    return mapper.readTree(reader);
  }

  public static Map readObjectsFromCsv(Reader reader) throws IOException {
    CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
    CsvMapper csvMapper = new CsvMapper();
    Map map = csvMapper.readValue(reader, Map.class);

    return map;
  }

  public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(file, data);
  }
}
