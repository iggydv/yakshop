package com.xebia.yakshop.models.mappers;

import com.xebia.api.models.LabYakRq;
import com.xebia.api.models.Sex;
import com.xebia.yakshop.models.LabYakInternal;
import com.xebia.yakshop.models.SexInternal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabYakMapperTest {

    private final LabYakMapper mapper = new LabYakMapperImpl();

    @Test
    void toApiModel() {
        LabYakInternal internal = LabYakInternal.builder().age(1.0).name("Joe").sex(SexInternal.F).ageLastShaved(0.0).build();
        LabYakRq result = mapper.toApiModel(internal);

        assertEquals(internal.getAge(), result.getAge());
        assertEquals(internal.getName(), result.getName());
        assertEquals(internal.getSex().label, result.getSex().getValue());
    }

    @Test
    void toInternalModel() {
        LabYakRq external = LabYakRq.builder().age(1.0).name("Joe").sex(Sex.M).build();
        LabYakInternal result = mapper.toInternalModel(external);

        assertEquals(external.getAge(), result.getAge());
        assertEquals(external.getName(), result.getName());
        assertEquals(external.getSex().getValue(), result.getSex().label);
        assertEquals(0.0, result.getAgeLastShaved());

        LabYakRq external1 = LabYakRq.builder().build();
        LabYakInternal result2 = mapper.toInternalModel(external1);

        assertEquals(0.0, result2.getAge());
        assertNull(result2.getName());
        assertNull(result2.getSex());
        assertEquals(0.0, result2.getAgeLastShaved());

    }
}