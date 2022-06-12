package com.xebia.yakshop.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockInternal {
    double milk;
    int skins;

    public boolean emptyOrder() {
        return hasNoSkins() && hasNoMilk();
    }
    public boolean hasNoMilk() {
        return milk == 0.0;
    }

    public boolean hasNoSkins() {
        return skins == 0;
    }
}
