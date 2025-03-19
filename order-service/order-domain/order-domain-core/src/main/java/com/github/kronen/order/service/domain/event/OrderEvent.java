package com.github.kronen.order.service.domain.event;

import com.github.kronen.domain.event.DomainEvent;
import com.github.kronen.order.service.domain.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class OrderEvent implements DomainEvent<Order> {

  private final Order order;

  private final Instant createdAt;
}
