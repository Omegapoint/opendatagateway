package com.omegapoint.opendatagateway.information_retrieval;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class InformationRetrievalScheduler {

    private static final long INTERVAL_MS = 120 * 1000; // 30 seconds

    private Timer timer = new Timer(false);
    private final ResourceUpdaterTask updaterTask;

    public InformationRetrievalScheduler() throws URISyntaxException {
        updaterTask = new ResourceUpdaterTask(readURIConfiguration());
        timer.scheduleAtFixedRate(updaterTask, 0L, INTERVAL_MS);
    }

    private static List<ApiData> readURIConfiguration() throws URISyntaxException {
        // TODO this should probably be established using some online resource or configuration file

        List<ApiData> resourceURIs = new ArrayList<>();
        //resourceURIs.add(new ApiData(new URIBuilder("http://bibstat.kb.se/export?sample_year=2016").build(), "bibstat", "xlsx"));
        //resourceAPIs.add(new URIBuilder("http://data.smhi.se/met/climate/time_series/html/rcp/swe/rcp85/data/t_ar_s_swe_rcp85.xls").build());
        resourceURIs.add(new ApiData(new URIBuilder("http://vattenweb.smhi.se/vatmarker/rest/download/V%C3%A5tmarker.xls").build(), "vatmarker", "xls"));
        return Collections.unmodifiableList(resourceURIs);
    }

    public void shutdown() {
        timer.cancel();
        updaterTask.shutdown();
    }

    private static class ResourceUpdaterTask extends TimerTask {

        private static final Timer timer = new Timer();

        private final Map<URI, LocalDateTime> latestUpdated = new HashMap<>();
        private final List<ApiData> resourceAPIs;
        private final Executor executor = new Executor();

        public ResourceUpdaterTask(List<ApiData> resourceURIs) {
            this.resourceAPIs = resourceURIs;
        }

        @Override
        public void run() {
            List<Future<InformationRetrievalResult>> results = new ArrayList<>();
            for (ApiData apiData : resourceAPIs) {
                System.out.println("Fetching: " + apiData.getUri());
                results.add(executor.submit(apiData));
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
