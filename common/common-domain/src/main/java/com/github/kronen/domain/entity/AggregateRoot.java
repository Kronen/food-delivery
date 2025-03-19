package com.github.kronen.domain.entity;

import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public abstract class AggregateRoot<ID> extends BaseEntity<ID> {}
