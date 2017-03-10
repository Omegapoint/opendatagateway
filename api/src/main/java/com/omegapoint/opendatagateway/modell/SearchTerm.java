package com.omegapoint.opendatagateway.modell;

import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

public class SearchTerm {
	private String field;
	private List<String> value;

	public SearchTerm() {
	}

	public SearchTerm(String field, List<String> value) {

		this.field = field;
		this.value = value;
	}

	public QueryBuilder asTermQuery() {
		return termQuery(field, value.get(0));
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}
}
