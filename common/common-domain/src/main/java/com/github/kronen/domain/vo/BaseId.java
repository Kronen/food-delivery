package com.github.kronen.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class BaseId<T> {

  private final T value;
}
