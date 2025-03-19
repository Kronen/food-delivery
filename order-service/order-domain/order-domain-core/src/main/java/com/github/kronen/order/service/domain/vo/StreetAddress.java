package com.github.kronen.order.service.domain.vo;

import java.util.UUID;

public record StreetAddress(UUID id, String street, String postalCode, String city) {}
