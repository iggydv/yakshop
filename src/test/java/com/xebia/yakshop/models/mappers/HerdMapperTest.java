package com.xebia.yakshop.models.mappers;

import com.xebia.api.models.HerdRq;
import com.xebia.api.models.LabYakRq;
import com.xebia.api.models.Sex;
import com.xebia.yakshop.models.HerdInternal;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HerdMapperTest {

    private final HerdMapper mapper = new HerdMapperImpl();

    @Test
    void toApiModel() {
    }

    @Test
    void test_empty_mapping() {
        HerdRq external = HerdRq.builder().build();
        HerdInternal result = mapper.toInternalModel(external);
        assertNull(result.getHerd());
    }

    @Test
    void test_to_internal_mapping_one_item() {
        HerdRq external = HerdRq.builder().herd(List.of(LabYakRq.builder().age(1.1).name("Joe").sex(Sex.F).build())).build();
        HerdInternal result = mapper.toInternalModel(external);
        assertEquals(1, result.getHerd().size());
        assertEquals(external.getHerd().get(0).getName(), result.getHerd().get(0).getName());
        assertEquals(external.getHerd().get(0).getAge(), result.getHerd().get(0).getAge());
        assertEquals(external.getHerd().get(0).getSex().getValue(), result.getHerd().get(0).getSex().label);
        assertEquals(0.0, result.getHerd().get(0).getAgeLastShaved());
    }

    @Test
    void test_to_internal_mapping_multiple_items() {
        HerdRq external = HerdRq.builder()
                .herd(List.of(
                        LabYakRq.builder().age(3.0).name("Phoebe").sex(Sex.F).build(),
                        LabYakRq.builder().age(2.0).name("Joey").sex(Sex.M).build(),
                        LabYakRq.builder().age(2.2).name("Ross").sex(Sex.M).build(),
                        LabYakRq.builder().age(1.2).name("Rachel").sex(Sex.F).build(),
                        LabYakRq.builder().age(1.8).name("Monica").sex(Sex.F).build(),
                        LabYakRq.builder().age(2.3).name("Chandler").sex(Sex.M).build()))
                .build();

        HerdInternal result = mapper.toInternalModel(external);
        assertEquals(6, result.getHerd().size());

        for (int i = 0; i < external.getHerd().size(); i++) {
            assertEquals(external.getHerd().get(i).getName(), result.getHerd().get(i).getName());
            assertEquals(external.getHerd().get(i).getAge(), result.getHerd().get(i).getAge());
            assertEquals(external.getHerd().get(i).getSex().getValue(), result.getHerd().get(i).getSex().label);
            assertEquals(0.0, result.getHerd().get(i).getAgeLastShaved());
        }

    }
}