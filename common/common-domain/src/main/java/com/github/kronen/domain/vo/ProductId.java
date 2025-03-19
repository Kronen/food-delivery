package com.github.kronen.domain.vo;

import java.util.UUID;

import lombok.Builder;

public class ProductId extends BaseId<UUID> {

  @Builder
  public ProductId(UUID value) {
    super(value);
  }
}
