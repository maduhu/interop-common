package com.l1p.interop.ilp.ledger;

import com.l1p.interop.ilp.ledger.domain.Credit;
import com.l1p.interop.ilp.ledger.domain.Debit;
import com.l1p.interop.ilp.ledger.domain.Transfer;

import java.net.MalformedURLException;

/**
 * Maps Ledger, Id, Account urls from dfsp specfic to ledger adapter specific urls
 */
public class LedgerUrlMapper {

  private final String urlRemappingRegex;
  private final String urlLedgerAdapterReplacementUrl;

  public LedgerUrlMapper(String urlRemappingRegex, String urlLedgerAdapterReplacementUrl) {
    this.urlRemappingRegex = urlRemappingRegex;
    this.urlLedgerAdapterReplacementUrl = urlLedgerAdapterReplacementUrl;
  }

  public void mapUrlToIlpAdapterLedger(Transfer transfer) throws MalformedURLException {
    transfer.setId(transfer.getId().replaceFirst(urlRemappingRegex, urlLedgerAdapterReplacementUrl));
    transfer.setLedger(transfer.getLedger().replaceFirst(urlRemappingRegex, urlLedgerAdapterReplacementUrl));

    for (Credit credit : transfer.getCredits()) {
      credit.setAccount(credit.getAccount().replaceFirst(urlRemappingRegex, urlLedgerAdapterReplacementUrl));
    }

    for (Debit debit : transfer.getDebits()) {
      debit.setAccount(debit.getAccount().replaceFirst(urlRemappingRegex, urlLedgerAdapterReplacementUrl));
    }
  }

}
