package com.acme.orders.batch.infra.writer;

import com.acme.orders.batch.domain.model.Order;
import com.acme.orders.batch.infra.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderJpaWriter implements ItemWriter<Order> {

  private final OrderRepository repository;

  @Override
  public void write(Chunk<? extends Order> items) {
    repository.saveAll(items);
  }
}
