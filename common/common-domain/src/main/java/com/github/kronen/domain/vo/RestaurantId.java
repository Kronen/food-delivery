package com.github.kronen.domain.vo;

import java.util.UUID;

import lombok.Builder;

public class RestaurantId extends BaseId<UUID> {

  @Builder
  public RestaurantId(UUID value) {
    super(value);
  }
}
