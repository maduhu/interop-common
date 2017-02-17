package com.l1p.interop.ilp.ledger.domain;

import java.util.List;

public class SubscriptionParams {
  private List<String> accounts;
  private String eventType;

  public List<String> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<String> accounts) {
    this.accounts = accounts;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }
}
