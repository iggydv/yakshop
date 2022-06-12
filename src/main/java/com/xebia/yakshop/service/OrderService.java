package com.xebia.yakshop.service;

import com.xebia.yakshop.models.OrderInternal;
import com.xebia.yakshop.models.StockInternal;
import com.xebia.yakshop.storage.HerdStorage;
import com.xebia.yakshop.storage.OrderHistoryStorage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class OrderService {
    private HerdStorage herd;
    private OrderHistoryStorage orderHistory;

    @Autowired
    public OrderService(HerdStorage herd, OrderHistoryStorage orderHistory) {
        this.herd = herd;
        this.orderHistory = orderHistory;
    }

    public StockInternal placeOrder(OrderInternal order, int day) {
        StockInternal expectedStock = herd.calculateStock(day);
        double milkStock = expectedStock.getMilk();
        int skinStock = expectedStock.getSkins();

        StockInternal.StockInternalBuilder updatedStock = StockInternal.builder();
        StockInternal.StockInternalBuilder result = StockInternal.builder();

        double milkOrder = order.getStock().getMilk();
        int skinsOrder = order.getStock().getSkins();

        double milkOrderHistory = orderHistory.getMilkOrderHistory();
        int skinsOrderHistory = orderHistory.getSkinOrderHistory();

        double actualMilkStock = milkStock - milkOrderHistory;
        int actualSkinStock = skinStock - skinsOrderHistory;

        if (actualMilkStock >= milkOrder) {
            result.milk(milkOrder);
            updatedStock.milk(milkOrderHistory + milkOrder);
        }

        if (actualSkinStock >= skinsOrder) {
            result.skins(skinsOrder);
            updatedStock.skins(skinsOrderHistory + skinsOrder);
        }

        orderHistory.addOrder(order.getCustomer(), updatedStock.build());
        return result.build();
    }
}
