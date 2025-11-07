package com.acme.orders.batch.infra.fx;

import com.acme.orders.batch.domain.ports.ExchangeRatePort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StaticExchangeRateAdapter implements ExchangeRatePort {
  @Override
  public BigDecimal convertToPEN(BigDecimal amount, String currencyCode) {
    // Static demo: 1 USD = 3.8 PEN, 1 EUR = 4.0 PEN
    BigDecimal rate = switch (currencyCode) {
      case "USD" -> new BigDecimal("3.8");
      case "EUR" -> new BigDecimal("4.0");
      default -> BigDecimal.ONE;
    };
    return amount.multiply(rate);
  }
}
