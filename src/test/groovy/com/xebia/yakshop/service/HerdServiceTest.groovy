package com.xebia.yakshop.service

import com.xebia.yakshop.models.LabYakInternal
import com.xebia.yakshop.models.SexInternal
import com.xebia.yakshop.storage.HerdStorageImpl
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class HerdServiceTest extends Specification {
    @Shared
    private final List<LabYakInternal> herd = List.of(
            LabYakInternal.builder().age(4.0).name("Betty-1").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(8.0).name("Betty-2").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(9.5).name("Betty-3").sex(SexInternal.F).build())

    @Subject
    private final HerdService herdService = new HerdService(new HerdStorageImpl(herd))

    def "should correctly calculate total amount of milk over period"() {
        when:
        def milkTotal = herdService.calculateTotalMilkForPeriod(T)

        then:
        assert milkTotal == expected

        where:
        T  | expected
        0  | 0.0
        1  | 85.5
        13 | 1104.48
    }

    def "should correctly calculate total skins over period"() {
        when:
        def skinTotal = herdService.calculateTotalSkinsForPeriod(T)

        then:
        assert skinTotal == expected

        where:
        T  | expected
        0  | 0
        1  | 3
        13 | 3
    }

    def "should correctly calculate total stock over period"() {
        when:
        def totalStock = herdService.calculateStock(T)

        then:
        assert totalStock.getSkins() == expectedSkins
        assert totalStock.getMilk() == expectedMilk

        where:
        T  | expectedSkins | expectedMilk
        0  | 0             | 0.0
        1  | 3             | 85.5
        13 | 3             | 1104.48
    }

    def "should correctly set age-last-shaved"() {
        when:
        herdService.calculateAgeLastShaved(day)

        then:
        def yak = herdService.getHerdStorage().getHerd().get(0)
        yak.getAgeLastShaved() == expected

        where:
        day | expected
        1   | 4.0
        13  | 4.13
        14  | 4.13
    }
}
