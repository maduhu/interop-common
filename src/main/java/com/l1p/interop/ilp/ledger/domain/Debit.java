package com.l1p.interop.ilp.ledger.domain;

import com.fasterxml.jackson.databind.JsonNode;

public class Debit extends Credit {
  private boolean authorized;

  public Debit() {
  }

  public Debit(String account, String amount, JsonNode memo, boolean authorized) {
    super(account, amount, memo);
    this.authorized = authorized;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void setAuthorized(boolean authorized) {
    this.authorized = authorized;
  }
}
