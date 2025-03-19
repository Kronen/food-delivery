package com.github.kronen.order.service.domain.entity;

import com.github.kronen.domain.entity.BaseEntity;
import com.github.kronen.domain.vo.Money;
import com.github.kronen.domain.vo.OrderId;
import com.github.kronen.order.service.domain.vo.OrderItemId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class OrderItem extends BaseEntity<OrderItemId> {

  private OrderId orderId;

  private final Product product;

  private final int quantity;

  private final Money price;

  private final Money subTotal;

  void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
    super.setId(orderItemId);
    this.orderId = orderId;
  }

  boolean isPriceValid() {
    return this.price.isGreaterThanZero()
        && price.equals(this.product.getPrice())
        && price.multiply(quantity).equals(subTotal);
  }
}
