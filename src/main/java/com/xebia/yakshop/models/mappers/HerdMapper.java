package com.xebia.yakshop.models.mappers;

import com.xebia.yakshop.storage.HerdStorageImpl;
import com.xebia.yakshop.models.HerdStatus;
import com.xebia.yakshop.models.LabYakRq;
import com.xebia.yakshop.models.LabYakRs;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class HerdMapper {
    public HerdStorageImpl toInternalModel(List<LabYakRq> source) {
        return HerdStorageImpl.builder()
                .herd(source.stream()
                        .map(LabYakMapper.INSTANCE::toInternalModel)
                        .collect(Collectors.toList()))
                .build();
    }

    public HerdStatus toHerdStatus(@NonNull HerdStorageImpl herdStorageImpl, int T) {
        List<LabYakRs> yaks = herdStorageImpl.getHerd()
                .stream()
                .map(labYakInternal -> LabYakMapper.INSTANCE.toApiResponseModel(labYakInternal, T))
                .collect(Collectors.toList());
        return HerdStatus.builder().herd(yaks).build();
    }
}
