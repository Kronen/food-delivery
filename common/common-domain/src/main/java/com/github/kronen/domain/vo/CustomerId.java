package com.github.kronen.domain.vo;

import java.util.UUID;

import lombok.Builder;

public class CustomerId extends BaseId<UUID> {

  @Builder
  public CustomerId(UUID value) {
    super(value);
  }
}
