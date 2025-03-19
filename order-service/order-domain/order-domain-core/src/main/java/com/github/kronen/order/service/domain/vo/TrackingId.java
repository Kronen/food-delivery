package com.github.kronen.order.service.domain.vo;

import java.util.UUID;

import com.github.kronen.domain.vo.BaseId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingId extends BaseId<UUID> {

  public TrackingId(UUID value) {
    super(value);
  }
}
