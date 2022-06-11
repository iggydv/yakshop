package com.xebia.yakshop.models

import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class LabYakInternalTest extends Specification {

    @Shared
    def yakFemale1 = LabYakInternal.builder().name("Cloe").age(4.0).sex(SexInternal.F).build()
    @Shared
    def yakFemale2 = LabYakInternal.builder().name("Cloe-1").age(1.0).sex(SexInternal.F).build()
    @Shared
    def yakMale = LabYakInternal.builder().name("Joey").age(1.0).sex(SexInternal.M).build()

    def "calculate milk produced for the day"() {
        given:
        def testYak = LabYakInternal.builder().name("Cloe").age(age).sex(SexInternal.F).build()

        when:
        def produced = testYak.milkProducedForTheDay(day)

        then:
        assert produced == expected

        where:
        age   | day | expected
        0.0   | 0   | 50.0
        0.0   | 7   | 49.79
        1.0   | 0   | 47.0
        1.0   | 8   | 46.76
        1.0   | 10  | 46.7
        4.0   | 0   | 38.0
        4.0   | 12  | 37.64
        4.0   | 13  | 37.61
        10.00 | 0   | 0.0
    }

    def "calculate when a yak can be shaved"() {
        given:
        def testYak = LabYakInternal.builder().name("Cloe").age(age).sex(SexInternal.F).build()

        when:
        def produced = testYak.canBeShaved(day)

        then:
        assert produced == expected

        where:
        age   | day | expected
        0.0   | 0   | false
        0.0   | 7   | false
        1.0   | 0   | true
        1.0   | 8   | false
        1.0   | 10  | true
        1.0   | 20  | true
        1.0   | 30  | true
        1.0   | 40  | true
        1.0   | 50  | true
        4.0   | 0   | true
        4.0   | 12  | false
        4.0   | 13  | true
        10.00 | 0   | false
    }

    def "calculate milk produced over time"() {
        when:
        def femaleYak1Produce = yakFemale1.milkProducedOverTime(day)
        def femaleYak2Produce = yakFemale2.milkProducedOverTime(day)
        def maleYakProduce = yakMale.milkProducedOverTime(day)

        then:
        assert femaleYak1Produce == femaleYak1Expected
        assert femaleYak2Produce == femaleYak2Expected
        assert maleYakProduce == maleExpected

        where:
        day | femaleYak1Expected | femaleYak2Expected | maleExpected
        0   | 0.0                | 0.0                | 0.0
        1   | 38.0               | 47.0               | 0.0
        2   | 75.97              | 93.97              | 0.0
        3   | 113.91             | 140.91             | 0.0
        4   | 151.82             | 187.82             | 0.0
        60  | 2226.90            | 2766.9             | 0.0
        200 | 7003.00            | 8803.0             | 0.0
        300 | 10054.5            | 12754.5            | 0.0
        600 | 17409.0            | 22809.0            | 0.0
        610 | 17409.0            | 23097.65           | 0.0
    }

    @Unroll
    def "calculate skins produced over time"() {
        given:
        def yakFemale1 = LabYakInternal.builder().name("Cloe").age(4.0).sex(SexInternal.F).build()
        def yakFemale2 = LabYakInternal.builder().age(9.5).name("Betty-3").sex(SexInternal.F).build()
        def yakMale = LabYakInternal.builder().name("Joey").age(8.0).sex(SexInternal.M).build()

        when:
        def femaleYak1Produce = yakFemale1.skinsProducedOverTime(day)
        def femaleYak2Produce = yakFemale2.skinsProducedOverTime(day)
        def maleYakProduce = yakMale.skinsProducedOverTime(day)

        then:
        assert femaleYak1Produce == femaleYak1Expected
        assert femaleYak2Produce == femaleYak2Expected
        assert maleYakProduce == maleExpected

        where:
        day | femaleYak1Expected | femaleYak2Expected | maleExpected
        0   | 0                  | 0                  | 0
        1   | 1                  | 1                  | 1
        13  | 1                  | 1                  | 1
        60  | 5                  | 3                  | 4
        200 | 16                 | 3                  | 13
        300 | 22                 | 3                  | 13
        600 | 42                 | 3                  | 13
        610 | 42                 | 3                  | 13
    }
}