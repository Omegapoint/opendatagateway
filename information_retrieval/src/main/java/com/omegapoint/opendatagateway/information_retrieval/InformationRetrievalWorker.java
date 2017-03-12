package com.omegapoint.opendatagateway.information_retrieval;

import com.omegapoint.opendatagateway.information_retrieval.response.handler.InformationRetrievalResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class InformationRetrievalWorker implements Callable<InformationRetrievalResult> {

	private final ApiData apiData;
	private final LocalDateTime latestUpdate;

	public InformationRetrievalWorker(ApiData apiData, LocalDateTime latestUpdate) {
		this.apiData = apiData;
		this.latestUpdate = latestUpdate;
	}

	@Override
	public InformationRetrievalResult call() throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(apiData.getUri());
		return client.execute(request, new InformationRetrievalResponseHandler(apiData, latestUpdate));
	}
}
