package com.omegapoint.opendatagateway.information_retrieval;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class InformationRetrievalScheduler {

    private static final long INTERVAL_MS = 10 * 1000; // 30 seconds

    private Timer timer = new Timer(false);
    private final ResourceUpdaterTask updaterTask;

    public InformationRetrievalScheduler() throws URISyntaxException {
        updaterTask = new ResourceUpdaterTask(readURIConfiguration());
        timer.scheduleAtFixedRate(updaterTask, 0L, INTERVAL_MS);
    }

    private static List<URI> readURIConfiguration() throws URISyntaxException {
        // TODO this should probably be established using some online resource or configuration file

        List<URI> resourceURIs = new ArrayList<>();
        resourceURIs.add(new URIBuilder("http://vattenweb.smhi.se/vatmarker/rest/download/V%C3%A5tmarker.xls").build());
        return Collections.unmodifiableList(resourceURIs);
    }

    public void shutdown() {
        timer.cancel();
        updaterTask.shutdown();
    }

    private static class ResourceUpdaterTask extends TimerTask {

        private static final Timer timer = new Timer();

        private final Map<URI, LocalDateTime> latestUpdated = new HashMap<>();
        private final List<URI> resourceURIs;
        private final Executor executor = new Executor();

        public ResourceUpdaterTask(List<URI> resourceURIs) {
            this.resourceURIs = resourceURIs;
        }

        @Override
        public void run() {
            List<Future<InformationRetrievalResult>> results = new ArrayList<>();
            for (URI uri : resourceURIs) {
                System.out.println("Fetching: " + uri);
                results.add(executor.submit(uri));
            }
            for (Future<InformationRetrievalResult> result : results) {
                try {
                    InformationRetrievalResult retrievalResult = result.get();
                    System.out.println("Resource at : " + retrievalResult.getUri() + ", was last updated " + retrievalResult.getLatestUpdated());
                    latestUpdated.put(retrievalResult.getUri(), retrievalResult.getLatestUpdated());
                } catch (InterruptedException e) {
                    // ignore interruptsions
                } catch (ExecutionException e) {
                    // TODO proper logging
                    e.printStackTrace();
                }
            }
        }

        public void shutdown() {
            executor.shutdown();
        }
    }
}
