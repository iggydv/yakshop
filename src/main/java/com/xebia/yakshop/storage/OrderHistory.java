package com.xebia.yakshop.storage;

import com.xebia.yakshop.models.StockInternal;

public interface OrderHistory {
    /**
     * Retrieve skin order history
     *
     * @return
     */
    int getSkinOrderHistory();

    /**
     * Retrieve milk order history
     *
     * @return
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
