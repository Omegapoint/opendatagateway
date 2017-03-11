package com.omegapoint.opendatagateway.information_retrieval;

import com.omegapoint.opendatagateway.information_retrieval.response.handler.InformationRetrievalResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class InformationRetrievalWorker implements Callable<InformationRetrievalResult> {

    private final URI serviceURI;
    private final LocalDateTime latestUpdate;

    public InformationRetrievalWorker(URI serviceURI, LocalDateTime latestUpdate) {
        this.serviceURI = serviceURI;
        this.latestUpdate = latestUpdate;
    }

    @Override
    public InformationRetrievalResult call() throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(serviceURI);
        return client.execute(request, new InformationRetrievalResponseHandler(serviceURI, latestUpdate));
    }
}
