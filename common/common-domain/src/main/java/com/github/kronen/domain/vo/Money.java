package com.github.kronen.domain.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

  public static final Money ZERO = new Money(BigDecimal.ZERO);

  public boolean isGreaterThanZero() {
    return this.amount.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean isGreaterThan(Money other) {
    return this.amount.compareTo(other.amount()) > 0;
  }

  public Money add(Money other) {
    return new Money(normalizeScale(this.amount.add(other.amount())));
  }

  public Money subtract(Money other) {
    return new Money(normalizeScale(this.amount.subtract(other.amount())));
  }

  public Money multiply(int factor) {
    return new Money(normalizeScale(this.amount.multiply(new BigDecimal(factor))));
  }

  private BigDecimal normalizeScale(BigDecimal input) {
    return input.setScale(2, RoundingMode.HALF_EVEN);
  }
}
