package com.omegapoint.opendatagateway.information_retrieval;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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
			new InformationRetrievalScheduler();
		} else {
			ClassLoader classLoader = getClass().getClassLoader();
			Publisher publisher = new Publisher("test");

			try {
				File input = new File(classLoader.getResource("test_input.xls").getFile());
				String data = XlsToCsv.xlsx(input,"xls");
				List<Map<?, ?>> nodes = CsvToJsonConverter.readObjectsFromCsv(data);

				Iterator<Map<?, ?>> i = nodes.iterator();
				while (i.hasNext()) {
					ObjectMapper mapper = new ObjectMapper();
					String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(i.next());
					//System.out.println(pretty);
					publisher.publishRow(pretty);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				publisher.endPublish();
			}

		}
	}

}
