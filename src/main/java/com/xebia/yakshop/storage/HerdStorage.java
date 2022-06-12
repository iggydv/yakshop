package com.xebia.yakshop.storage;

import com.xebia.yakshop.models.LabYakInternal;
import com.xebia.yakshop.models.StockInternal;

import java.util.List;

public interface HerdStorage {

    void setHerd(List<LabYakInternal> herd);

    List<LabYakInternal> getHerd();

    /**
     * Calculate the herd's total stock (milk & skin production) over the period T
     *
     * @param T the amount of days to calculate milk production for
     * @return the total amount of milk production in liters
     */
    StockInternal calculateStock(int T);

    /**
     * Calculate when all Yaks have last been shaved. Mostly used as a utility method, to ensure that all yaks have
     * a valid & accurate ageLastShaved field.
     *
     * @param T the amount of days that have passed
     */
    void calculateAgeLastShaved(int T);
}
