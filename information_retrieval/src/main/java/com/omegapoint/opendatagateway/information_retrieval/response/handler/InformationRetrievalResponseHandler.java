package com.omegapoint.opendatagateway.information_retrieval.response.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omegapoint.opendatagateway.information_retrieval.ApiData;
import com.omegapoint.opendatagateway.information_retrieval.CsvToJsonConverter;
import com.omegapoint.opendatagateway.information_retrieval.InformationRetrievalResult;
import com.omegapoint.opendatagateway.information_retrieval.Publisher;
import com.omegapoint.opendatagateway.information_retrieval.XlsToCsv;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InformationRetrievalResponseHandler implements ResponseHandler<InformationRetrievalResult> {

	public static final String LAST_MODIFIED_HEADER = "Last-Modified";
	private static final String DEFAULT_ENCODING_CHARSET = "UTF-8";
	private final ApiData apiData;
	private final LocalDateTime latestUpdate;

	public InformationRetrievalResponseHandler(ApiData apiData, LocalDateTime latestUpdate) {
		this.apiData = apiData;
		this.latestUpdate = latestUpdate;
	}

	@Override
	public InformationRetrievalResult handleResponse(HttpResponse response) throws ClientProtocolException,
			IOException {
		Header[] lastModifiedHeaders = response.getHeaders(LAST_MODIFIED_HEADER);
		LocalDateTime maxDateTime = null;
		if (lastModifiedHeaders != null) {
			for (Header lastModifiedHeader : lastModifiedHeaders) {
				String dateTimeString = lastModifiedHeader.getValue();
				LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
				if (maxDateTime == null || maxDateTime.compareTo(dateTime) < 0) {
					maxDateTime = dateTime;
				}
			}
			if (maxDateTime == null) {
				maxDateTime = LocalDateTime.now();
			}
			if (!maxDateTime.isAfter(latestUpdate)) {
				return new InformationRetrievalResult(apiData.getUri(), latestUpdate);
			}
		}

		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
			return handleResponseEntity(maxDateTime, response.getEntity());
		}

		return new InformationRetrievalResult(apiData.getUri(), latestUpdate);
	}

	private InformationRetrievalResult handleResponseEntity(LocalDateTime latestUpdate, HttpEntity entity)
			throws IOException {
		System.err.println("handleResponseEntity" + entity.getContentLength());
		insertData(convertData(entity.getContent()));
		return new InformationRetrievalResult(apiData.getUri(), latestUpdate);
	}

	private List<Map<?, ?>> convertData(InputStream stream) throws IOException {
		String data = XlsToCsv.xlsx(stream, apiData.getType());
		//dumpToFile(data);
		List<Map<?, ?>> nodes = CsvToJsonConverter.readObjectsFromCsv(data);
		return nodes;
	}

	private void dumpToFile(String data) {
		System.out.println("Data: " + data);
		try {
			PrintWriter writer = new PrintWriter("/tmp/data.txt", "UTF-8");
			writer.println(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertData(List<Map<?, ?>> nodes) throws JsonProcessingException, UnsupportedEncodingException {
		Publisher publisher = new Publisher(URLEncoder.encode(apiData.getName(), "UTF-8"));
		System.err.println("InsertData: " + nodes.size());
		for (Map<?, ?> node : nodes) {
			ObjectMapper mapper = new ObjectMapper();
			String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
			publisher.publishRow(pretty);
		}
		publisher.endPublish();
	}

}
