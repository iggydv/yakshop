package com.xebia.yakshop.models.mappers;

import com.xebia.yakshop.models.HerdInternal;
import com.xebia.yakshop.models.LabYakRq;
import com.xebia.yakshop.models.Sex;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HerdMapperTest {

    private final HerdMapper mapper = new HerdMapper();

    @Test
    void test_empty_mapping() {
        HerdInternal result = mapper.toInternalModel(Collections.emptyList());
        assertNotNull(result.getHerd());
    }

    @Test
    void test_to_internal_mapping_one_item() {
        List<LabYakRq> external = List.of(LabYakRq.builder().age(1.1).name("Joe").sex(Sex.F).build());
        HerdInternal result = mapper.toInternalModel(external);
        assertEquals(1, result.getHerd().size());
        assertEquals(external.get(0).getName(), result.getHerd().get(0).getName());
        assertEquals(external.get(0).getAge(), result.getHerd().get(0).getAge());
        assertEquals(external.get(0).getSex().getValue(), result.getHerd().get(0).getSex().label);
        assertEquals(0.0, result.getHerd().get(0).getAgeLastShaved());
    }

    @Test
    void test_to_internal_mapping_multiple_items() {
        List<LabYakRq> external = List.of(
                LabYakRq.builder().age(3.0).name("Phoebe").sex(Sex.F).build(),
                LabYakRq.builder().age(2.0).name("Joey").sex(Sex.M).build(),
                LabYakRq.builder().age(2.2).name("Ross").sex(Sex.M).build(),
                LabYakRq.builder().age(1.2).name("Rachel").sex(Sex.F).build(),
                LabYakRq.builder().age(1.8).name("Monica").sex(Sex.F).build(),
                LabYakRq.builder().age(2.3).name("Chandler").sex(Sex.M).build());

        HerdInternal result = mapper.toInternalModel(external);
        assertEquals(6, result.getHerd().size());

        for (int i = 0; i < external.size(); i++) {
            assertEquals(external.get(i).getName(), result.getHerd().get(i).getName());
            assertEquals(external.get(i).getAge(), result.getHerd().get(i).getAge());
            assertEquals(external.get(i).getSex().getValue(), result.getHerd().get(i).getSex().label);
            assertEquals(0.0, result.getHerd().get(i).getAgeLastShaved());
        }

    }
}