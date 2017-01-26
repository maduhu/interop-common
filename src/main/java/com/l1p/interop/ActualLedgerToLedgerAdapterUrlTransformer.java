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
 * Tranforms JSON representation of Interop Object's Actual Ledger specific urls to Ledger Adapter Specific Urls
 */
public class ActualLedgerToLedgerAdapterUrlTransformer extends AbstractMessageTransformer {
  private static final Logger log = LoggerFactory.getLogger(ActualLedgerToLedgerAdapterUrlTransformer.class);
  public static final String INTEROP_ID = "interopID";

  private LedgerUrlMapper ledgerUrlMapper;
  protected final ObjectMapper mapper = new ObjectMapper();

  private String[] urlFields = {"id", "ledger", "debits.account", "credits.account", "sourceAccount", "account"};

  public ActualLedgerToLedgerAdapterUrlTransformer() {
  }

  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
    try {
      final String genericJson = (String) message.getPayload();
      log.info("interopID:{}, Interop Object JSON: {}", message.getProperty(INTEROP_ID, PropertyScope.SESSION), genericJson);
      Map<String, Object> interopGenericMap = mapper.readValue(genericJson, new TypeReference<Map<String, Object>>() { });
      ledgerUrlMapper.mapUrlToLedgerAdapterUrl(urlFields, interopGenericMap);
      return mapper.writeValueAsString(interopGenericMap);
    } catch (IOException e) {
      final String msg = "Failed to map from actual ledger to ledger adapter urls";
      log.error(msg, e);
      throw new TransformerException(MessageFactory.createStaticMessage(msg));
    }
  }

  public LedgerUrlMapper getLedgerUrlMapper() {
    return ledgerUrlMapper;
  }

  public void setLedgerUrlMapper(LedgerUrlMapper ledgerUrlMapper) {
    this.ledgerUrlMapper = ledgerUrlMapper;
  }

  public String[] getUrlFields() {
    return urlFields;
  }

  public void setUrlFields(String[] urlFields) {
    this.urlFields = urlFields;
  }

}
