package com.l1p.interop.ilp.ledger.domain;

import java.util.HashMap;

public class TransferParams extends NotificationParams {
  public static final String TRANSFER_CREATE = "transfer.create";
  public static final String TRANSFER_UPDATE = "transfer.update";

  public TransferParams(String event, Object resource) {
    super(event, resource);
  }

}
