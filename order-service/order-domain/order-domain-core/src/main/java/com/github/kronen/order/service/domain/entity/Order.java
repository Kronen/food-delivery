package com.github.kronen.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

import com.github.kronen.domain.entity.AggregateRoot;
import com.github.kronen.domain.vo.CustomerId;
import com.github.kronen.domain.vo.Money;
import com.github.kronen.domain.vo.OrderId;
import com.github.kronen.domain.vo.OrderStatus;
import com.github.kronen.domain.vo.RestaurantId;
import com.github.kronen.order.service.domain.exception.OrderDomainException;
import com.github.kronen.order.service.domain.vo.OrderItemId;
import com.github.kronen.order.service.domain.vo.StreetAddress;
import com.github.kronen.order.service.domain.vo.TrackingId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Order extends AggregateRoot<OrderId> {

  private final CustomerId customerId;

  private final RestaurantId restaurantId;

  private final StreetAddress deliveryAddress;

  private final Money price;

  private final List<OrderItem> items;

  private TrackingId trackingId;

  private OrderStatus orderStatus;

  private List<String> failureMessages;

  public void initializeOrder() {
    this.setId(new OrderId(UUID.randomUUID()));
    this.trackingId = new TrackingId(UUID.randomUUID());
    this.orderStatus = OrderStatus.PENDING;
    this.initializeOrderItem();
  }

  private void initializeOrderItem() {
    long itemId = 1L;
    for (OrderItem orderItem : this.items) {
      orderItem.initializeOrderItem(this.getId(), new OrderItemId(itemId++));
    }
  }

  public void validateOrder() {
    validateInitialOrder();
    validateTotalPrice();
    validateItemsPrice();
  }

  public void pay() {
    if (!OrderStatus.PENDING.equals(this.orderStatus)) {
      throw new OrderDomainException("Order is not in correct state for pay operation!");
    }
    orderStatus = OrderStatus.PAID;
  }

  public void approve() {
    if (this.orderStatus != OrderStatus.PAID) {
      throw new OrderDomainException("Order is not in correct state for approve operation!");
    }
    orderStatus = OrderStatus.APPROVED;
  }

  public void initCancel(List<String> failureMessages) {
    if (this.orderStatus != OrderStatus.PAID) {
      throw new OrderDomainException("Order is not in correct state for initCancel operation!");
    }
    orderStatus = OrderStatus.CANCELLING;
    updateFailureMessages(failureMessages);
  }

  private void updateFailureMessages(List<String> failureMessages) {
    Optional.ofNullable(this.failureMessages)
      .orElseGet(ArrayList::new)
      .addAll(failureMessages.stream().filter(Predicate.not(String::isBlank)).toList());
  }

  public void cancel() {
    if (!(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING)) {
      throw new OrderDomainException("Order is not in correct state for cancel operation!");
    }
    orderStatus = OrderStatus.CANCELLED;
  }

  private void validateInitialOrder() {
    if (orderStatus != null || getId() != null) {
      throw new OrderDomainException("Order is not in correct state for initialization");
    }
  }

  private void validateTotalPrice() {
    if (price == null || !price.isGreaterThanZero()) {
      throw new OrderDomainException("Total price must be greater than zero");
    }
  }

  private void validateItemsPrice() {
    Money orderItemsTotal = items.stream()
        .map(orderItem -> {
          validateItemPrice(orderItem);
          return orderItem.getSubTotal();
        })
        .reduce(Money.ZERO, Money::add);

    if (!price.equals(orderItemsTotal)) {
      throw new OrderDomainException(
          String.format("Total price: %s is not equal to order items total: %s", price, orderItemsTotal));
    }
  }

  private void validateItemPrice(OrderItem orderItem) {
    if (!orderItem.isPriceValid()) {
      throw new OrderDomainException(String.format(
          "Order item price %s is not valid for product %s",
          orderItem.getPrice(), orderItem.getProduct().getId().getValue()));
    }
  }
}
