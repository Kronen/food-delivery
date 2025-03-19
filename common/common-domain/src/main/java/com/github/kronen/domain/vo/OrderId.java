package com.github.kronen.domain.vo;

import java.util.UUID;

import lombok.Builder;

public class OrderId extends BaseId<UUID> {

  @Builder
  public OrderId(UUID value) {
    super(value);
  }
}
