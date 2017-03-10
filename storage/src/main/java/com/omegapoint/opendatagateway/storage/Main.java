package com.omegapoint.opendatagateway.storage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Main {


	public static void main(String argv[]) {


		System.out.println("Hello World!");

		try {
			HttpClient httpclient = HttpClients.createDefault();

			HttpPost httppost = new HttpPost("http://localhost:9200/opendatagateway/natur/1");
			StringEntity requestEntity = new StringEntity(
					"{\"title\":\"hej\",\"text\":\"ddd\"}",
					ContentType.APPLICATION_JSON);

			httppost.setEntity(requestEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			if (resEntity != null) {
				System.out.println("Response content length: " + resEntity.getContentLength());
				System.out.println("Chunked?: " + resEntity.isChunked());
				String responseBody = EntityUtils.toString(resEntity);
				System.out.println("Data: " + responseBody);
			}
			EntityUtils.consume(resEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


}
