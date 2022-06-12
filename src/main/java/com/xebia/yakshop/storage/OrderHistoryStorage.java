package com.xebia.yakshop.storage;

import com.xebia.yakshop.models.StockInternal;

public interface OrderHistoryStorage {
    /**
     * Retrieve skin order history
     *
     */
    int getSkinOrderHistory();

    /**
     * Retrieve milk order history
     *
     */
    double getMilkOrderHistory();

    /**
     * Add an order to the order history
     *
     * @param customerName name of the customer
     * @param order        the amount of milk/skin ordered
     */
    void addOrder(String customerName, StockInternal order);

    /**
     * Reset The orderHistory
     */
    void reset();
}
