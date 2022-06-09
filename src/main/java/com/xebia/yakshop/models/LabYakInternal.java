package com.xebia.yakshop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LabYakInternal {
    final String name;
    final double age;
    final SexInternal sex;
    @Builder.Default
    double ageLastShaved = 0.0;

    public double milkProducedOverTime(int T) {
        if (sex.equals(SexInternal.F)) {
            double milk = 0.0;
            for (int i = 0; i <= T; i++) {
                milk += milkProducedForTheDay(getAgeInDays() + i);
            }
            return milk;
        }
        return 0.0; // assuming that male yaks don't produce milk
    }

    public int hidesProducedOverTime(int T) {
        int hides = 0;
        for (int i = 0; i <= T; i++) {
            if (canBeShaved(getAgeInDays() + i, i)) {
                hides++;
            }
        }
        return hides;
    }

    public double milkProducedForTheDay(double yakAgeDays) {
        if (yakAgeDays > 1000.0) {
            return 0.0;
        }
        return 50 - (yakAgeDays * 0.03);
    }

    public boolean canBeShaved(double yakAgeDays, int today) {
        if (yakAgeDays >= 100.0 && yakAgeDays < 1000.0) {
            int nextShaveDay = (int) Math.ceil(8 + (yakAgeDays * 0.01));
            if (today == 0 || today == nextShaveDay) {
                // all yaks are eligible to be shaved on day 0
                this.ageLastShaved = today;
                return true;
            }
        }
        return false;
    }

    public double getAgeInDays() {
        return age * 100.00;
    }
}
