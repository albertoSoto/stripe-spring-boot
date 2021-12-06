package com.albertosoto.stripe.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Product {
    @Getter
    @Setter
    String id;
    @Getter
    @Setter
    Boolean active;
    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    List<Price> prices;
}
