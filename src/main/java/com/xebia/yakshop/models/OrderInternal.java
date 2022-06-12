package com.xebia.yakshop.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderInternal {
    String customer;
    StockInternal stock;
}
