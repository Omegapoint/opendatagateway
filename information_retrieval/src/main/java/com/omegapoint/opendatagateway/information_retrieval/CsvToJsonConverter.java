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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CsvToJsonConverter {

	public static JsonNode toJson(Reader reader) throws IOException {
		CsvMapper mapper = new CsvMapper();
		return mapper.readTree(reader);
	}

	public static List<Map<?, ?>> readObjectsFromCsv(String data) throws IOException {
		CsvSchema bootstrap = CsvSchema.emptySchema().withHeader().withColumnSeparator('|');
		CsvMapper csvMapper = new CsvMapper();
		MappingIterator<Map<?, ?>> mappingIterator = csvMapper
				.reader(Map.class)
				.with(bootstrap)
				.readValues(new StringReader(data));
		System.err.println("readObjectsFromCsv adding maps to list");

		List<Map<?, ?>> maps = new ArrayList<>();
		while (mappingIterator.hasNext()) {
			System.err.print(".");
			Map<?, ?> map = mappingIterator.nextValue();
			maps.add(map);
		}

		System.err.println("readObjectsFromCsv DONE!");
		return maps;
	}

	public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(file, data);
	}
}
