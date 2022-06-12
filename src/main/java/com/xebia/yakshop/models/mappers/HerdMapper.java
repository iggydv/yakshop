package com.xebia.yakshop.models.mappers;

import com.xebia.yakshop.storage.HerdStorage;
import com.xebia.yakshop.storage.HerdStorageImpl;
import com.xebia.yakshop.models.HerdStatus;
import com.xebia.yakshop.models.LabYakRq;
import com.xebia.yakshop.models.LabYakRs;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class HerdMapper {

    static HerdMapper INSTANCE;

    public static HerdMapper getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HerdMapper();
        }

        return INSTANCE;
    }

    public HerdStorageImpl toInternalModel(List<LabYakRq> source) {
        return HerdStorageImpl.builder()
                .herd(source.stream()
                        .map(LabYakMapper.INSTANCE::toInternalModel)
                        .collect(Collectors.toList()))
                .build();
    }

    public HerdStatus toHerdStatus(@NonNull HerdStorage herdStorage, int T) {
        List<LabYakRs> yaks = herdStorage.getHerd()
                .stream()
                .map(labYakInternal -> LabYakMapper.INSTANCE.toApiResponseModel(labYakInternal, T))
                .collect(Collectors.toList());
        return HerdStatus.builder().herd(yaks).build();
    }
}
