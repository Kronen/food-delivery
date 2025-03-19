package com.github.kronen.order.service.domain.entity;

import com.github.kronen.domain.entity.AggregateRoot;
import com.github.kronen.domain.vo.CustomerId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Customer extends AggregateRoot<CustomerId> {}
