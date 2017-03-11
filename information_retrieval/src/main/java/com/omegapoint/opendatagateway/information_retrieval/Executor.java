package com.omegapoint.opendatagateway.information_retrieval;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Executor {

    private static final int MAX_THREADS = 10;
    private final ThreadPoolExecutor executor;

    public Executor() {
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);
    }

    public final Future<InformationRetrievalResult> submit(URI uri) {
        return submit(uri, LocalDateTime.MIN);
    }

    public final Future<InformationRetrievalResult> submit(URI uri, LocalDateTime latestUpdated) {
        return executor.submit(new InformationRetrievalWorker(uri, latestUpdated));
    }

    public void shutdown() {
        executor.shutdown();
    }
}
