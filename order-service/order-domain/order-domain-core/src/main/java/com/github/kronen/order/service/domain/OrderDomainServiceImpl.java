package com.github.kronen.order.service.domain;

import com.github.kronen.domain.vo.ProductId;
import com.github.kronen.order.service.domain.entity.Order;
import com.github.kronen.order.service.domain.entity.Product;
import com.github.kronen.order.service.domain.entity.Restaurant;
import com.github.kronen.order.service.domain.event.OrderCancelledEvent;
import com.github.kronen.order.service.domain.event.OrderCreatedEvent;
import com.github.kronen.order.service.domain.event.OrderPaidEvent;
import com.github.kronen.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

  @Override
  public OrderCreatedEvent createOrder(Order order, Restaurant restaurant) {
    validateRestaurant(restaurant);
    syncProductDetailsWithRestaurant(order, restaurant);
    order.validateOrder();
    order.initializeOrder();
    log.info("Order with id: {} is created", order.getId().getValue());
    return OrderCreatedEvent.builder().order(order).createdAt(Instant.now()).build();
  }

  @Override
  public OrderPaidEvent payOrder(Order order) {
    order.pay();
    log.info("Order with id: {} is paid", order.getId().getValue());
    return OrderPaidEvent.builder().order(order).createdAt(Instant.now()).build();
  }

  @Override
  public void approveOrder(Order order) {
    order.approve();
    log.info("Order with id {} is approved", order.getId().getValue());
  }

  @Override
  public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
    order.initCancel(failureMessages);
    log.info("Order payment is cancelling with id {}", order.getId().getValue());
    return OrderCancelledEvent.builder().order(order).createdAt(Instant.now()).build();
  }

  @Override
  public void cancelOrder(Order order, List<String> failureMessages) {
    order.cancel();
    log.info("Order with id {} is cancelled", order.getId().getValue());
  }

  private void validateRestaurant(Restaurant restaurant) {
    if (!restaurant.isActive()) {
      throw new OrderDomainException("Restaurant is not active");
    }
  }

  private void syncProductDetailsWithRestaurant(Order order, Restaurant restaurant) {
    Map<ProductId, Product> restaurantProducts = restaurant.getProducts().stream()
      .collect(Collectors.toMap(Product::getId, Function.identity()));

    order.getItems().forEach(orderItem -> {
      Product currentProduct = orderItem.getProduct();
      Product restaurantProduct = restaurantProducts.get(currentProduct.getId());
      if (restaurantProduct != null) {
        currentProduct.applyConfirmedProductDetails(restaurantProduct);
      }
    });
  }


}
