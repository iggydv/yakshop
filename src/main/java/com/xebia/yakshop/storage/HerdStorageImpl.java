package com.xebia.yakshop.storage;

import com.xebia.yakshop.models.LabYakInternal;
import com.xebia.yakshop.models.StockInternal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Component
@Scope("singleton")
public class HerdStorageImpl implements HerdStorage {
    @Builder.Default
    List<LabYakInternal> herd = new ArrayList<>();

    @Override
    public StockInternal calculateStock(int T) {
        double totalMilk = calculateTotalMilkForPeriod(T);
        int totalSkins = calculateTotalSkinsForPeriod(T);
        return StockInternal.builder().milk(totalMilk).skins(totalSkins).build();
    }

    private double calculateTotalMilkForPeriod(int T) {
        double total = 0.0;
        for (LabYakInternal yak : herd) {
            total += yak.milkProducedOverTime(T);
        }
        return total;
    }

    public int calculateTotalSkinsForPeriod(int T) {
        int total = 0;
        for (LabYakInternal yak : herd) {
            int skins = yak.skinsProducedOverTime(T);
            total += skins;
        }
        return total;
    }

    public void calculateAgeLastShaved(int T) {
        herd.forEach(labYakInternal -> labYakInternal.calculateAgeLastShaved(T));
    }

}
