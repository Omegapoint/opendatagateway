package com.omegapoint.opendatagateway.information_retrieval.response.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omegapoint.opendatagateway.information_retrieval.CsvToJsonConverter;
import com.omegapoint.opendatagateway.information_retrieval.InformationRetrievalResult;
import com.omegapoint.opendatagateway.information_retrieval.Publisher;
import com.omegapoint.opendatagateway.information_retrieval.XlsToCsv;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Optional;

public class InformationRetrievalResponseHandler implements ResponseHandler<InformationRetrievalResult> {

    public static final String LAST_MODIFIED_HEADER = "Last-Modified";
    private static final String DEFAULT_ENCODING_CHARSET = "UTF-8";
    private String name;

  public InformationRetrievalResponseHandler(String name) {
    this.name = name;
  }

  @Override
    public InformationRetrievalResult handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        Header[] lastModifiedHeaders = response.getHeaders(LAST_MODIFIED_HEADER);
        LocalDateTime maxDateTime = LocalDateTime.MIN;
        if (lastModifiedHeaders != null) {
            for (Header lastModifiedHeader : lastModifiedHeaders) {
                String dateTimeString = lastModifiedHeader.getValue();
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
                if (maxDateTime.compareTo(dateTime) < 0) {
                    maxDateTime = dateTime;
                }
            }
        }
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
            return handleResponseEntity(maxDateTime, response.getEntity());
        }
        // Really???? Really?? Returning null are you?? Sloppy!!
        return null;
    }

    private InformationRetrievalResult handleResponseEntity(LocalDateTime dateTime, HttpEntity entity)
        throws IOException {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), contentEncodingCharset(entity)));
        insertData(convertData(entity.getContent()));
        return new InformationRetrievalResult(dateTime);
    }

    private String contentEncodingCharset(HttpEntity entity) {
        Header encoding = entity.getContentEncoding();
        if (encoding == null) {
            return DEFAULT_ENCODING_CHARSET;
        }

        return Optional.ofNullable(encoding.getValue()).orElse(DEFAULT_ENCODING_CHARSET);
    }

    private List<Map<?, ?>> convertData(InputStream stream) throws IOException {
      String data = XlsToCsv.xlsx(stream);
      System.out.println("Data: " + data);
      List<Map<?, ?>> nodes = CsvToJsonConverter.readObjectsFromCsv(data);
      return nodes;
    }

    private void insertData(List<Map<?, ?>> nodes) throws JsonProcessingException {
      Iterator<Map<?, ?>> i = nodes.iterator();
      while (i.hasNext()) {
        ObjectMapper mapper = new ObjectMapper();
        String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(i.next());
        System.out.println(pretty);
        Publisher.publish(name, pretty);
      }
    }

}
