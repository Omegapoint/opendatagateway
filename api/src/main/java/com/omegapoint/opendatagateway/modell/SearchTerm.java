package com.omegapoint.opendatagateway.modell;

import org.elasticsearch.index.query.QueryBuilder;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

public class SearchTerm {
	private String field;
	private String value;

	public SearchTerm() {
	}

	public SearchTerm(String field, String value) {
		this.field = field;
		this.value = value;
	}

	public QueryBuilder asTermQuery() {
		return termQuery(field, value);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
