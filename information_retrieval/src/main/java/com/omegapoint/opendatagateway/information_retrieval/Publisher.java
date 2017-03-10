package com.omegapoint.opendatagateway.information_retrieval;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by joek0073 on 2017-03-10.
 */
public class Publisher {
	public static void publish(String index, String json) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:9200/opendatagateway/" + index);
		StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
		httpPost.setEntity(requestEntity);
		try {

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			if (resEntity != null) {
				System.out.println("Response content length: " + resEntity.getContentLength());
				System.out.println("Chunked?: " + resEntity.isChunked());
				String responseBody = EntityUtils.toString(resEntity);
				System.out.println("Data: " + responseBody);
			}
			EntityUtils.consume(resEntity);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
