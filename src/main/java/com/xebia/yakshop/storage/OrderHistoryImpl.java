package com.xebia.yakshop.storage;

import com.xebia.yakshop.models.StockInternal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderHistoryImpl implements OrderHistory {
    @Builder.Default
    LinkedMultiValueMap<String, StockInternal> orders = new LinkedMultiValueMap<>();

    @Override
    public int getSkinOrderHistory() {
        return orders.values().stream().mapToInt(value -> value.stream().mapToInt(StockInternal::getSkins).sum()).sum();
    }

    @Override
    public double getMilkOrderHistory() {
        return orders.values().stream().mapToDouble(value -> value.stream().mapToDouble(StockInternal::getMilk).sum()).sum();
    }

    @Override
    public void addOrder(String customerName, StockInternal order) {
        orders.add(customerName, order);
    }

    @Override
    public void reset() {
        orders.clear();
    }
}
