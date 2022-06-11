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

    void "test mapping from internal to API model"() {
        given:
        LabYakInternal internal = LabYakInternal.builder().age(1.0).name("Joe").sex(SexInternal.F).ageLastShaved(0.0).build()

        when:
        LabYakRq result = mapper.toApiModel(internal)

        then:
        assert internal.getAge() == result.getAge()
        assert internal.getName() == result.getName()
        assert internal.getSex().label == result.getSex().getValue()
    }

    void "test mapping from API model to internal model"() {
        when:
        LabYakInternal result = mapper.toInternalModel(yak)

        then:
        assert result.getAge() == expectedAge
        assert result.getName() == expectedName
        assert result.getSex().label == expectedSex.getValue()
        assert result.getAgeLastShaved() == 0.0

        where:
        yak                                                        | expectedAge  | expectedName  | expectedSex
        LabYakRq.builder().age(1.0).name("Joe").sex(Sex.M).build() | yak.getAge() | yak.getName() | yak.getSex()
    }
}