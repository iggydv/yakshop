package com.xebia.yakshop.models

import spock.lang.Specification
import spock.lang.Subject

class HerdInternalTest extends Specification {

    private final List<LabYakInternal> herd = List.of(
            LabYakInternal.builder().age(4.0).name("Betty-1").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(8.0).name("Betty-2").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(9.5).name("Betty-3").sex(SexInternal.F).build())

    @Subject
    private final HerdInternal herdInternal = new HerdInternal(herd);

    def "Calculate Total Milk For Period"() {
        when:
        double milkTotal = herdInternal.calculateTotalMilkForPeriod(T)

        then:
        assert milkTotal == expected

        where:
        T  | expected
        0  | 0.0
        1  | 85.5
        13 | 1104.48
    }

    def "CalculateTotalSkinsForPeriod"() {
        when:
        double skinTotal = herdInternal.calculateTotalSkinsForPeriod(T)

        then:
        assert skinTotal == expected

        where:
        T  | expected
        0  | 0
        1  | 3
        13 | 3
    }
}
