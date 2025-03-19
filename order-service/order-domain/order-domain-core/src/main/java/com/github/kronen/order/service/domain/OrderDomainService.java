package com.github.kronen.order.service.domain;

import com.github.kronen.order.service.domain.entity.Order;
import com.github.kronen.order.service.domain.entity.Restaurant;
import com.github.kronen.order.service.domain.event.OrderCancelledEvent;
import com.github.kronen.order.service.domain.event.OrderCreatedEvent;
import com.github.kronen.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

  OrderCreatedEvent createOrder(Order order, Restaurant restaurant);

  OrderPaidEvent payOrder(Order order);

  void approveOrder(Order order);

  OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

  void cancelOrder(Order order, List<String> failureMessages);
}
