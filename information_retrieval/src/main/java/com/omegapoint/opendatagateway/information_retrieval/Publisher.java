package com.omegapoint.opendatagateway.information_retrieval;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by joek0073 on 2017-03-10.
 */
public class Publisher {

	private HttpClient httpClient;
	private HttpPost httpPost;

	public Publisher(String index) {
		httpClient = setupHttpClient();
		httpPost = new HttpPost("http://elasticsearch:9200/opendatagateway/" + index);
	}

	private CloseableHttpClient setupHttpClient() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials
				= new UsernamePasswordCredentials("elastic", "changeme");
		provider.setCredentials(AuthScope.ANY, credentials);

		return HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider)
				.build();
	}

	public void publishRow(String json) {
		System.err.println("Publish row:");
		System.err.println(json);
		try {
			StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(requestEntity);
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

	public void endPublish() {
		httpClient = null;
		httpPost = null;
	}

}
