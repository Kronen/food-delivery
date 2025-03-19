package com.github.kronen.domain.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity<ID> {

  private ID id;
}
