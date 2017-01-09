package com.l1p.interop.ilp.ledger;

import com.l1p.interop.ilp.ledger.domain.Credit;
import com.l1p.interop.ilp.ledger.domain.Debit;
import com.l1p.interop.ilp.ledger.domain.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Maps Ledger, Id, Account urls from dfsp specfic to ledger adapter specific urls
 */
public class LedgerUrlMapper {
  private static final Logger log = LoggerFactory.getLogger(LedgerUrlMapper.class);

  private final String ledgerAdapterUrlMappingRegex;
  private final String ledgerAdapterReplacementUrl;
  private final String actualLedgerUrlMappingRegex;
  private final String actualLedgerReplacementUrl;


  public LedgerUrlMapper(String ledgerAdapterUrlMappingRegex, String ledgerAdapterReplacementUrl, String actualLedgerUrlMappingRegex, String actualLedgerReplacementUrl) {
    this.ledgerAdapterUrlMappingRegex = ledgerAdapterUrlMappingRegex;
    this.ledgerAdapterReplacementUrl = ledgerAdapterReplacementUrl;
    this.actualLedgerUrlMappingRegex = actualLedgerUrlMappingRegex;
    this.actualLedgerReplacementUrl = actualLedgerReplacementUrl;
  }

  public void mapUrlToIlpAdapterLedger(Transfer transfer) throws MalformedURLException {
    transfer.setId(transfer.getId().replaceFirst(ledgerAdapterUrlMappingRegex, ledgerAdapterReplacementUrl));
    transfer.setLedger(transfer.getLedger().replaceFirst(ledgerAdapterUrlMappingRegex, ledgerAdapterReplacementUrl));

    for (Credit credit : transfer.getCredits()) {
      credit.setAccount(credit.getAccount().replaceFirst(ledgerAdapterUrlMappingRegex, ledgerAdapterReplacementUrl));
    }

    for (Debit debit : transfer.getDebits()) {
      debit.setAccount(debit.getAccount().replaceFirst(ledgerAdapterUrlMappingRegex, ledgerAdapterReplacementUrl));
    }
  }

  public void mapUrlToLedgerAdapterUrl(final String[] fields, final Map<String, Object> map) {
    mapUrls(fields, map, ledgerAdapterUrlMappingRegex, ledgerAdapterReplacementUrl);
  }

  public void mapUrlToActualLedgerUrl(final String[] fields, final Map<String, Object> map) {
    mapUrls(fields, map, actualLedgerUrlMappingRegex, actualLedgerReplacementUrl);
  }

  private void mapUrls(final String[] fields, final Map<String, Object> targetMap, final String regex, final String replacementUrl) {
    for (String field : fields) { // iterate through all fields and map url
      final String[] keys = field.split("\\.");
      Map<String, Object> currentMap = targetMap;
      for (int i = 0; i < keys.length; i++) {
        String key = keys[i];
        final Object currentValue = currentMap.get(key);
        if (currentValue == null) {
          log.warn("field: {} not found in the map for URL transform", field);
          break;
        }
        if (currentValue instanceof Map) {
          currentMap = (Map) currentValue;
        } else if (currentValue instanceof String) {
          final String mappedUrl = ((String) currentValue).replaceFirst(regex, replacementUrl);
          currentMap.put(key, mappedUrl);
          break;
        } else if (currentValue instanceof List) {
          List fieldList = (List) currentValue;
          for (Object fieldFromList : fieldList) {
            if (fieldFromList instanceof Map) {
              String remainingFieldName = extractRemainingField(field, i + 1);
              mapUrls(new String[] {remainingFieldName}, (Map) fieldFromList, regex, replacementUrl);
            }
          }
          break;
        } else {  // unexpected type
          log.error("Unexpected type:{} found in target Map while mapping to ledger URL for field:{}", currentValue.getClass(), field);
          break;
        }
      }
    }
  }

  private String extractRemainingField(final String field, final int startingToken) {
    String remainingString = field;
    for(int i = 0; i < startingToken; i++ ) {
      remainingString = remainingString.substring(remainingString.indexOf('.') + 1);
    }
    return remainingString;
  }

}
