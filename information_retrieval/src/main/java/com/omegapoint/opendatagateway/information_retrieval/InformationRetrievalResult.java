package com.omegapoint.opendatagateway.information_retrieval;

import java.time.LocalDateTime;

public class InformationRetrievalResult {
    private final LocalDateTime dateTime;

    public InformationRetrievalResult(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
