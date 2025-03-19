package com.github.kronen.order.service.domain.entity;

import com.github.kronen.domain.entity.BaseEntity;
import com.github.kronen.domain.vo.Money;
import com.github.kronen.domain.vo.ProductId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Product extends BaseEntity<ProductId> {

  private String name;

  private Money price;

  public void applyConfirmedProductDetails(Product product) {
    this.name = product.getName();
    this.price = product.getPrice();
  }
}
