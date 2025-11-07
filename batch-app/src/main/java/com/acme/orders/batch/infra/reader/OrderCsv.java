package com.acme.orders.batch.infra.reader;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderCsv {
  private String orderId;
  private String customerId;
  private BigDecimal amount;
  private String currency;
  private String createdAt;
}
