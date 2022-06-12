package com.xebia.yakshop.models.mappers

import com.xebia.yakshop.models.LabYakRq
import com.xebia.yakshop.models.Sex
import com.xebia.yakshop.storage.HerdStorageImpl
import spock.lang.Specification
import spock.lang.Subject

class HerdMapperTest extends Specification {

    @Subject
    private final HerdMapper mapper = new HerdMapper();

    def "test empty mapping"() {
        given:
        HerdStorageImpl result = mapper.toInternalModel(Collections.emptyList());

        expect:
        assert result.getHerd() != null
    }

    def "test to internal mapping one item"() {
        given:
        List<LabYakRq> external = List.of(LabYakRq.builder().age(1.1).name("Joe").sex(Sex.F).build())

        when:
        HerdStorageImpl result = mapper.toInternalModel(external);

        then:
        assert result.getHerd().size() == 1
        assert result.getHerd().get(0).getName() == external.get(0).getName()
        assert result.getHerd().get(0).getAge() == external.get(0).getAge()
        assert result.getHerd().get(0).getSex().label == external.get(0).getSex().getValue()
        assert result.getHerd().get(0).getAgeLastShaved() == 0.0
    }

    def "test to internal mapping multiple items"() {
        given:
        List<LabYakRq> external = List.of(
                LabYakRq.builder().age(3.0).name("Phoebe").sex(Sex.F).build(),
                LabYakRq.builder().age(2.0).name("Joey").sex(Sex.M).build(),
                LabYakRq.builder().age(2.2).name("Ross").sex(Sex.M).build(),
                LabYakRq.builder().age(1.2).name("Rachel").sex(Sex.F).build(),
                LabYakRq.builder().age(1.8).name("Monica").sex(Sex.F).build(),
                LabYakRq.builder().age(2.3).name("Chandler").sex(Sex.M).build());

        when:
        HerdStorageImpl result = mapper.toInternalModel(external);

        then:
        assert result.getHerd().size() == 6

        for (int i = 0; i < external.size(); i++) {
            assert result.getHerd().get(i).getName() == external.get(i).getName()
            assert result.getHerd().get(i).getAge() == external.get(i).getAge()
            assert result.getHerd().get(i).getSex().label == external.get(i).getSex().getValue()
            assert result.getHerd().get(i).getAgeLastShaved() == 0.0
        }

    }
}