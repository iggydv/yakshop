package com.xebia.yakshop.models.mappers;

//import com.xebia.api.models.HerdRq;

import com.xebia.api.models.LabYakRq;
import com.xebia.yakshop.models.HerdInternal;

import java.util.List;
import java.util.stream.Collectors;

public class HerdMapper {
    public HerdInternal toInternalModel(List<LabYakRq> source) {
        return HerdInternal.builder()
                .herd(source.stream()
                        .map(LabYakMapper.INSTANCE::toInternalModel)
                        .collect(Collectors.toList()))
                .build();
    }
}
