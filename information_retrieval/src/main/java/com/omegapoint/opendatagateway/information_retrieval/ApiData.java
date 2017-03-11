package com.omegapoint.opendatagateway.information_retrieval;

import java.net.URI;

public class ApiData {
  private URI uri;
  private String name;
  private String type;

  public ApiData(URI uri, String name, String type) {
    this.uri = uri;
    this.name = name;
    this.type = type;
  }

  public URI getUri() {
    return uri;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }
}
