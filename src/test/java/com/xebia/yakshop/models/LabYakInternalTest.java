package com.xebia.yakshop.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabYakInternalTest {

    private LabYakInternal yakFemale1 = LabYakInternal.builder().name("Cloe").age(4.0).sex(SexInternal.F).build();
    private LabYakInternal yakFemale2 = LabYakInternal.builder().name("Cloe").age(1.0).sex(SexInternal.F).build();
    private LabYakInternal yakMale = LabYakInternal.builder().name("Cloe").age(1.0).sex(SexInternal.M).build();

    @Test
    void milkProducedForTheDay() {
        assertEquals(38.0, yakFemale1.milkProducedForTheDay(yakFemale1.getAgeInDays()));
    }

    @Test
    void canBeShaved() {
        assertTrue(yakFemale1.canBeShaved(yakFemale1.getAgeInDays(), 0));
        assertTrue(yakFemale1.canBeShaved(yakFemale1.getAgeInDays(), 12));
        assertFalse(yakFemale1.canBeShaved(yakFemale1.getAgeInDays(), 13));
        assertFalse(yakFemale1.canBeShaved(1000.00, 0));
    }

    @Test
    void milkProducedOverTime() {
        assertEquals(0.0, yakMale.milkProducedOverTime(13));
        assertEquals(38.0, yakFemale1.milkProducedOverTime(0));
        assertEquals(529.27, yakFemale1.milkProducedOverTime(13));
        assertEquals(655.27, yakFemale2.milkProducedOverTime(13));
    }

    @Test
    void hidesProducedOverTime() {
        assertEquals(1, yakMale.hidesProducedOverTime(0));
        assertEquals(1, yakMale.hidesProducedOverTime(9));
        // shave on the day
        assertEquals(2, yakMale.hidesProducedOverTime(10));
        assertEquals(2, yakMale.hidesProducedOverTime(13));
    }
}