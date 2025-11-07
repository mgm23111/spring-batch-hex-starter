package com.acme.orders.batch.domain.ports;

import java.math.BigDecimal;

public interface ExchangeRatePort {
  BigDecimal convertToPEN(BigDecimal amount, String currencyCode);
}
