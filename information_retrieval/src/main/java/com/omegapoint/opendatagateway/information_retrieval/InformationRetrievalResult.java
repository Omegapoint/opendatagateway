package com.omegapoint.opendatagateway.information_retrieval;

import java.net.URI;
import java.time.LocalDateTime;

public class InformationRetrievalResult {
    private final LocalDateTime latestUpdated;
    private java.net.URI uri;

    public InformationRetrievalResult(URI uri, LocalDateTime latestUpdated) {
        this.uri = uri;
        this.latestUpdated = latestUpdated;
    }

    public URI getUri() {
        return uri;
    }

    public LocalDateTime getLatestUpdated() {
        return latestUpdated;
    }
}
