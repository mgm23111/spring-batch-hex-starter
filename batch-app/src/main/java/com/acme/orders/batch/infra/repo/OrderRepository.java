package com.acme.orders.batch.infra.repo;

import com.acme.orders.batch.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findByOrderId(String orderId);
}
