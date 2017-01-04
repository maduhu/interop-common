package com.l1p.interop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.l1p.interop.ilp.ledger.LedgerUrlMapper;
import com.l1p.interop.ilp.ledger.domain.Transfer;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Tranforms JSON representation of Transfer Object's DFSP Ledger specific urls to Ledger Adapter Specific Urls
 */
public class LedgerUrlMapperComponent extends AbstractMessageTransformer {
  private static final Logger log = LoggerFactory.getLogger(LedgerUrlMapperComponent.class);

  private final LedgerUrlMapper ledgerUrlMapper;
  private final ObjectMapper mapper = new ObjectMapper();

  public LedgerUrlMapperComponent(LedgerUrlMapper ledgerUrlMapper) {
    this.ledgerUrlMapper = ledgerUrlMapper;
  }

  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
    try {
      final String transferJson = (String) message.getPayload();
      log.info("Transform Transfer JSON: {}", transferJson);
      final Transfer transfer = mapper.readValue(transferJson, Transfer.class);
      ledgerUrlMapper.mapUrlToIlpAdapterLedger(transfer);
      return mapper.writeValueAsString(transfer);
    } catch (IOException e) {
      final Message msg = MessageFactory.createStaticMessage("Failed to map ledger urls");
      throw new TransformerException(msg);
    }
  }
}
