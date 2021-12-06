package com.albertosoto.stripe.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Price {
    @Getter
    @Setter
    String id;
    @Getter
    @Setter
    String lookUpKey;
    @Getter
    @Setter
    String currency;
    @Getter
    @Setter
    String interval;
    @Getter
    @Setter
    Boolean active;
    @Getter
    @Setter
    Long unitAmount;
    @Getter
    @Setter
    String type;
}
