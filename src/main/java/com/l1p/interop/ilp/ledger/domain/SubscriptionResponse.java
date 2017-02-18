package com.l1p.interop.ilp.ledger.domain;

public class SubscriptionResponse {

  private int id;
  private String jsonrpc;
  private int result;

  public SubscriptionResponse(int id, String jsonrpc, int size) {
    this.id = id;
    this.jsonrpc = jsonrpc;
    this.result = size;
  }

  public SubscriptionResponse() {
  }

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

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }
}
