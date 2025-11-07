package com.acme.orders.batch.jobs.processors;

import com.acme.orders.batch.domain.model.Order;
import com.acme.orders.batch.infra.reader.OrderCsv;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OrderProcessorConfig {

  @Bean
  public CompositeItemProcessor<OrderCsv, Order> compositeProcessor(
      OrderValidationProcessor v, EnrichExchangeRateProcessor e) {
    CompositeItemProcessor<OrderCsv, Order> c = new CompositeItemProcessor<>();
    c.setDelegates(List.of(v, e));
    return c;
  }
}
