package com.omegapoint.opendatagateway;

import com.omegapoint.opendatagateway.modell.SearchTerm;
import com.omegapoint.opendatagateway.modell.Vatmark;
import com.omegapoint.opendatagateway.repositories.VatmarkerRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;


@Controller
@EnableAutoConfiguration
public class ApiController {

	@Autowired
	private VatmarkerRepository repository;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@RequestMapping("/vat/format")
	@ResponseBody
	String format() {
		return "Hello World!";
	}

	@RequestMapping("/vat/search")
	@ResponseBody
	List<Vatmark> search(@RequestBody List<SearchTerm> searchTerms) {
		BoolQueryBuilder builder = boolQuery().queryName("name");
		for (SearchTerm searchTerm : searchTerms) {
			builder.must(searchTerm.asTermQuery());
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
		return elasticsearchTemplate.queryForList(searchQuery, Vatmark.class);
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

	@RequestMapping("/search")
	@ResponseBody
	String search() {
		return "Hello World!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiController.class, args);
	}

}

