package com.omegapoint.opendatagateway;

import com.omegapoint.opendatagateway.modell.SearchInfo;
import com.omegapoint.opendatagateway.modell.SearchTerm;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Controller
@EnableAutoConfiguration
public class ApiController {

	@RequestMapping("/vat/format")
	@ResponseBody
	String format() {
		return "Hello World!";
	}

	@RequestMapping("/vat/search")
	@ResponseBody
	List<String> search(@RequestBody String index, @RequestBody List<SearchTerm> searchTerms) throws
			UnknownHostException {
		return Collections.emptyList();
	}


	@RequestMapping("/format")
	@ResponseBody
	String format(@RequestBody String resource) {
		return "Hello World!";
	}

	@RequestMapping("/list")
	@ResponseBody
	String listResources() {
		return "Hello World!";
	}

	@PostMapping("/search")
	@ResponseBody
	String search(@RequestBody SearchInfo searchInfo) throws IOException {
		BoolQueryBuilder builder = boolQuery().queryName("name");
		for (SearchTerm searchTerm : searchInfo.searchTerms()) {
			builder.must(matchQuery(searchTerm.getField(), searchTerm.getValue()));
		}

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials
				= new UsernamePasswordCredentials("elastic", "changeme");
		provider.setCredentials(AuthScope.ANY, credentials);

		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig
				.Builder("http://elasticsearch:9200")
				.credentialsProvider(provider)
				.multiThreaded(true)
				.build());
		JestClient client = factory.getObject();

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(builder);

		System.err.println(searchSourceBuilder.toString());
		Search search = new Search.Builder(searchSourceBuilder.toString())
				// multiple index or types can be added.
				.addIndex(searchInfo.getIndex())
				.addType("http://vattenweb.smhi.se/vatmarker/rest/download/V%C3%A5tmarker.xls")
				.build();

		SearchResult result = client.execute(search);
		return "All is well \n" + result.getJsonString();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiController.class, args);
	}

}

