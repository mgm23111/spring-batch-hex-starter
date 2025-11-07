package com.acme.orders.batch.jobs.processors;

import com.acme.orders.batch.domain.model.Order;
import com.acme.orders.batch.domain.ports.ExchangeRatePort;
import com.acme.orders.batch.infra.reader.OrderCsv;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
class OrderValidationProcessor implements ItemProcessor<OrderCsv, Order> {
  @Override
  public Order process(OrderCsv in) {
    if (in.getAmount() == null || in.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Invalid amount for order " + in.getOrderId());
    }
    Order o = Order.builder()
        .orderId(in.getOrderId())
        .customerId(in.getCustomerId())
        .amount(in.getAmount())
        .currency(in.getCurrency())
        .createdAt(OffsetDateTime.parse(in.getCreatedAt()))
        .build();
    return o;
  }
}

@Component
@RequiredArgsConstructor
class EnrichExchangeRateProcessor implements ItemProcessor<OrderCsv, Order> {
  private final ExchangeRatePort fx;

  @Override
  public Order process(OrderCsv in) throws Exception {
    Order o = new OrderValidationProcessor().process(in);
    BigDecimal pen = fx.convertToPEN(o.getAmount(), o.getCurrency());
    o.setAmountPen(pen);
    return o;
  }
}
