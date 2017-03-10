package com.omegapoint.opendatagateway.information_retrieval;

import com.omegapoint.opendatagateway.information_retrieval.response.handler.InformationRetrievalResponseHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.util.concurrent.Callable;

public class InformationRetrievalWorker implements Callable<InformationRetrievalResult> {

    private final URI serviceURI;
    private final String name;

    public InformationRetrievalWorker(URI serviceURI) {
        this.serviceURI = serviceURI;
        this.name = getNameFromUri(serviceURI);
    }

    @Override
    public InformationRetrievalResult call() throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(serviceURI);
        return client.execute(request, new InformationRetrievalResponseHandler(name));
    }

    private String getNameFromUri(URI uri) {
        String name = FilenameUtils.removeExtension(uri.getPath());
        try {
            return URLEncoder.encode(name,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return name;
        }
    }
}
