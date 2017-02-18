package com.l1p.interop.ilp.ledger.domain;

public class SubscriptionRequest {

  private int id;
  private String jsonrpc;
  private String method;
  private SubscriptionParams params;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getJsonrpc() {
    return jsonrpc;
  }

  public void setJsonrpc(String jsonrpc) {
    this.jsonrpc = jsonrpc;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public SubscriptionParams getParams() {
    return params;
  }

  public void setParams(SubscriptionParams params) {
    this.params = params;
  }
}
