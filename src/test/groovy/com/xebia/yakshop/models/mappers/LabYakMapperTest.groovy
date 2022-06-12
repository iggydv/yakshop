package com.xebia.yakshop.models.mappers

import com.xebia.yakshop.models.LabYakInternal
import com.xebia.yakshop.models.LabYakRq
import com.xebia.yakshop.models.Sex
import com.xebia.yakshop.models.SexInternal
import spock.lang.Specification
import spock.lang.Subject

class LabYakMapperTest extends Specification {

    @Subject
    private final LabYakMapper mapper = new LabYakMapperImpl();

    def "should correctly map LabYakInternal -> LabYakRq"() {
        given:
        def internal = LabYakInternal.builder().age(1.0).name("Joe").sex(SexInternal.F).ageLastShaved(0.0).build()

        when:
        LabYakRq result = mapper.toApiModel(internal)

        then:
        assert internal.getAge() == result.getAge()
        assert internal.getName() == result.getName()
        assert internal.getSex().label == result.getSex().getValue()
    }

    def "should correctly map LabYakRq -> LabYakInternal"() {
        given:
        def yak = LabYakRq.builder().age(1.0).name("Joe").sex(Sex.M).build()

        when:
        def result = mapper.toInternalModel(yak)

        then:
        assert result.getAge() == yak.getAge()
        assert result.getName() == yak.getName()
        assert result.getSex().label == yak.getSex().getValue()
        assert result.getAgeLastShaved() == 0.0
    }
}