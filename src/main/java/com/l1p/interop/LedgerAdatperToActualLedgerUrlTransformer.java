package com.l1p.interop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.l1p.interop.ilp.ledger.LedgerUrlMapper;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Tranforms JSON representation of Interop Object's Actual Ledger specific urls to Actual(DFSP) Adapter Specific Urls
 */
public class LedgerAdatperToActualLedgerUrlTransformer extends ActualLedgerToLedgerAdapterUrlTransformer {
  private static final Logger log = LoggerFactory.getLogger(LedgerAdatperToActualLedgerUrlTransformer.class);

  public LedgerAdatperToActualLedgerUrlTransformer() {
  }

  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
    try {
      final String genericJson = (String) message.getPayload();
      log.info("interopID:{}, Transform Transfer JSON: {}", message.getProperty(INTEROP_ID, PropertyScope.SESSION), genericJson);
      Map<String, Object> interopGenericMap = mapper.readValue(genericJson, new TypeReference<Map<String, Object>>() {});
      getLedgerUrlMapper().mapUrlToActualLedgerUrl(getUrlFields(), interopGenericMap);
      return mapper.writeValueAsString(interopGenericMap);
    } catch (IOException e) {
      final String msg = "Failed to map from ledger adapter to actual ledger urls";
      log.error(msg, e);
      throw new TransformerException(MessageFactory.createStaticMessage(msg));
    }
  }

}
