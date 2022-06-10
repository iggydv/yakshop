package com.xebia.yakshop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Scope("singleton")
public class HerdInternal {
    @Builder.Default
    List<LabYakInternal> herd = new ArrayList<>();

    public double calculateTotalMilkForPeriod(int T) {
        double total = 0.0;
        for (LabYakInternal yak : herd) {
            total += yak.milkProducedOverTime(T);
        }
        return total;
    }

    public int calculateTotalSkinsForPeriod(int T) {
        int total = 0;
        for (LabYakInternal yak : herd) {
            total += yak.hidesProducedOverTime(T);
        }
        return total;
    }
}
