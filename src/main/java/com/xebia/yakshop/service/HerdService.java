package com.xebia.yakshop.service;

import com.xebia.yakshop.models.LabYakInternal;
import com.xebia.yakshop.models.StockInternal;
import com.xebia.yakshop.storage.HerdStorage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class HerdService {
    private final HerdStorage herdStorage;

    @Autowired
    public HerdService(HerdStorage herdStorage) {
        this.herdStorage = herdStorage;
    }

    public void loadHerd(HerdStorage newHerd) {
        herdStorage.setHerd(newHerd.getHerd());
    }

    /**
     * Calculate the herd's total stock (milk & skin production) over the period T
     *
     * @param T the amount of days to calculate milk production for
     * @return the total amount of milk production in liters
     */
    public StockInternal calculateStock(int T) {
        double totalMilk = calculateTotalMilkForPeriod(T);
        int totalSkins = calculateTotalSkinsForPeriod(T);
        return StockInternal.builder().milk(totalMilk).skins(totalSkins).build();
    }

    private double calculateTotalMilkForPeriod(int T) {
        double total = 0.0;
        for (LabYakInternal yak : herdStorage.getHerd()) {
            total += yak.milkProducedOverTime(T);
        }
        return total;
    }

    private int calculateTotalSkinsForPeriod(int T) {
        int total = 0;
        for (LabYakInternal yak : herdStorage.getHerd()) {
            int skins = yak.skinsProducedOverTime(T);
            total += skins;
        }
        return total;
    }

    /**
     * Calculate when all Yaks have last been shaved. Mostly used as a utility method, to ensure that all yaks have
     * a valid & accurate ageLastShaved field.
     *
     * @param T the amount of days that have passed
     */
    public void calculateAgeLastShaved(int T) {
        herdStorage.getHerd().forEach(labYakInternal -> labYakInternal.calculateAgeLastShaved(T));
    }
}
