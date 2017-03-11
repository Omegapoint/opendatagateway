package com.omegapoint.opendatagateway.modell;

import org.elasticsearch.index.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

public class SearchInfo {
	private String index;
	private String type;
	private List<SearchTerm> searchTerms = new ArrayList<>();

	public SearchInfo() {
	}

	public SearchInfo(String index, String type, List<SearchTerm> searchTerms) {
		this.index = index;
		this.type = type;
		this.searchTerms = searchTerms;
	}


	public List<SearchTerm> searchTerms() {
		return searchTerms;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SearchTerm> getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(List<SearchTerm> searchTerms) {
		this.searchTerms = searchTerms;
	}
}
