package com.xebia.yakshop.models.mappers;

import com.xebia.api.models.Herd;
import com.xebia.yakshop.models.HerdInternal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LabYakMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HerdMapper {
    HerdMapper INSTANCE = Mappers.getMapper(HerdMapper.class);

    Herd toApiModel(HerdInternal source);
    HerdInternal toInternalModel(Herd source);
}
