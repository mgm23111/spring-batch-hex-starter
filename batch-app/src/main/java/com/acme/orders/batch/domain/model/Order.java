package com.acme.orders.batch.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(name="uk_order_natural", columnNames = "orderId"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String orderId;

  private String customerId;
  private BigDecimal amount;
  private String currency;
  private BigDecimal amountPen;
  private OffsetDateTime createdAt;
}
