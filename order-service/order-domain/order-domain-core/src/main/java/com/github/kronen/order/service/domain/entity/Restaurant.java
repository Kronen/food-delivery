package com.github.kronen.order.service.domain.entity;

import java.util.List;

import com.github.kronen.domain.entity.AggregateRoot;
import com.github.kronen.domain.vo.RestaurantId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Restaurant extends AggregateRoot<RestaurantId> {

  private final List<Product> products;

  private boolean active;
}
