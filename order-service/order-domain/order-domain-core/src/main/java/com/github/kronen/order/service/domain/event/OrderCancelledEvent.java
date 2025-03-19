package com.github.kronen.order.service.domain.event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class OrderCancelledEvent extends OrderEvent {}
