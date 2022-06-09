package com.xebia.yakshop.models.mappers;

import com.xebia.api.models.HerdRq;
import com.xebia.yakshop.models.HerdInternal;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LabYakMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface HerdMapper {
    HerdMapper INSTANCE = Mappers.getMapper(HerdMapper.class);

    HerdRq toApiModel(HerdInternal source);
    HerdInternal toInternalModel(HerdRq source);
}
