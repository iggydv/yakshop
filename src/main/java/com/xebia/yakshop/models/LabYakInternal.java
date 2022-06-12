package com.xebia.yakshop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class LabYakInternal {
    @NonNull
    final String name;
    @NonNull
    final SexInternal sex;
    @NonNull
    Double age;
    @Builder.Default
    double ageLastShaved = 0.0;

    /**
     * When querying your app and placing orders, the elapsed time
     * in days (T) is used. A value of for instance 13 means that day
     * 12 has elapsed, and we're at midnight day 13 (00:00).
     * Growth of day T is therefore not taken into account.
     *
     * @param T days elapsed
     * @return milk produced in litres, over the given period
     */
    public double milkProducedOverTime(int T) {
        if (sex.equals(SexInternal.F)) {
            double milk = 0.0;
            for (int i = 0; i < T; i++) {
                milk += milkProducedForTheDay(i);
            }
            return round(milk);
        }
        // assuming that male yaks don't produce milk
        return 0.0;
    }

    /**
     * When querying your app and placing orders, the elapsed time
     * in days (T) is used. A value of for instance 13 means that day
     * 12 has elapsed, and we're at midnight day 13 (00:00).
     * Growth of day T is therefore not taken into account.
     *
     * @param T days elapsed
     * @return skins produced over the given period
     */
    public int skinsProducedOverTime(int T) {
        int hides = 0;
        for (int i = 0; i < T; i++) {
            if (canBeShaved(i)) {
                hides++;
            }
        }
        return hides;
    }

    public double milkProducedForTheDay(int day) {
        double yakAge = getCurrentAge(day);
        if (yakAge >= 1000.0) {
            return 0.0;
        }
        return round(50 - (yakAge * 0.03));
    }

    // All yaks (> 1 year) are eligible to be shaved on day 0
    public boolean canBeShaved(int today) {
        double yakAgeDays = getCurrentAge(today);
        if (yakAgeDays >= 100.0 && yakAgeDays < 1000.0) {
            calculateAgeLastShaved(today);
            return yakAgeDays == getAgeInDays(this.ageLastShaved);
        }
        return false;
    }

//    public int hidesProducedOverTime2(int day, int skins) {
//        int hides = 0;
//
//        // No days have passed
//        if (day == 0) {
//            return hides;
//        }
//        double currentYakAge = getCurrentAge(day - 1);
//
//        // calculate the centennial age of the yak i.e. 100, 200, 300 etc
//        if (currentYakAge >= 100.0 && currentYakAge < 1000.0) {
//            // spans multiple algorithms
//            if (currentYakAge - getAgeInDays() > 100) {
//                double a = currentYakAge / 100.0;
//                hidesProducedOverTime2()
//            }
//            int shaveIncrementForAge = shaveIncrement(currentYakAge);
//            calculateAgeLastShaved(currentYakAge);
//            hides = (int) Math.floor((currentYakAge - getAgeInDays()) / shaveIncrementForAge) + 1;
//        }
//        return hides;
//    }

    public int shaveIncrement(double currentAge) {
        // each 100 days the shave interval changes
        return (int) Math.ceil(8 + getCentennial(currentAge) * 0.01) + 1;
    }

    public void calculateAgeLastShaved(int today) {
        double yakAgeDays = getCurrentAge(today);
        int shaveIncrement = shaveIncrement(yakAgeDays);
        if (today == 0) {
            this.ageLastShaved = yakAgeDays / 100;
        }

        if (today > 0 && this.ageLastShaved == 0 && this.age > 1) {
            this.ageLastShaved = this.age;
        }

        double nextShaveDay = getAgeInDays(this.ageLastShaved) + shaveIncrement;
        if (round(yakAgeDays - getAgeInDays()) % shaveIncrement == 0) {
            nextShaveDay = yakAgeDays;
        }

        if (yakAgeDays == nextShaveDay) {
            this.ageLastShaved = yakAgeDays / 100;
        }
    }

    public int getCentennial(double currentAge) {
        return (int) (currentAge / 100) * 100;
    }

    public double getCurrentAge(int T) {
        return getAgeInDays() + T;
    }

    public double getAgeInDays() {
        return age * 100.00;
    }

    public double getAgeInDays(double yakYears) {
        return yakYears * 100.00;
    }

    private double round(double num) {
        return Math.round(num * 100.0) / 100.0;
    }
}
