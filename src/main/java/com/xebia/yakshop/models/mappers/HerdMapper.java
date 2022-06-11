package com.xebia.yakshop.models.mappers;

//import com.xebia.api.com.xebia.yakshop.models.HerdRq;


import com.xebia.yakshop.models.HerdInternal;
import com.xebia.yakshop.models.HerdStatus;
import com.xebia.yakshop.models.LabYakRq;
import com.xebia.yakshop.models.LabYakRs;

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

    public HerdStatus toHerdStatus(HerdInternal herdInternal, int T) {
        List<LabYakRs> yaks = herdInternal.getHerd()
                .stream()
                .map(labYakInternal -> LabYakMapper.INSTANCE.toApiResponseModel(labYakInternal, T))
                .collect(Collectors.toList());
        return HerdStatus.builder().herd(yaks).build();
    }

}
