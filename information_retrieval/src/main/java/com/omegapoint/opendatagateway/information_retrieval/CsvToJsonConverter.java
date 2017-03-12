package com.omegapoint.opendatagateway.information_retrieval;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;


public class CsvToJsonConverter {

	public static List<Map<?, ?>> readObjectsFromCsv(String data) throws IOException {
		CsvSchema bootstrap = CsvSchema.emptySchema().withHeader().withColumnSeparator('|');
		CsvMapper csvMapper = new CsvMapper();
		MappingIterator<Map<?, ?>> mappingIterator = csvMapper
				.reader(Map.class)
				.with(bootstrap)
				.readValues(new StringReader(data));
		List<Map<?, ?>> maps = mappingIterator.readAll();
		return maps;
	}
}
