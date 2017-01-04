package com.l1p.interop.ilp.ledger.domain;

public class MessageParams extends NotificationParams {

  public MessageParams(String event, Object resource) {
    super("message.send", resource);
  }

}
