package com.github.kronen.order.service.domain.vo;

import com.github.kronen.domain.vo.BaseId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemId extends BaseId<Long> {

  public OrderItemId(Long value) {
    super(value);
  }
}
