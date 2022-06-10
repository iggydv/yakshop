package com.xebia.yakshop.models.mappers;

import com.xebia.api.models.LabYakRq;
import com.xebia.yakshop.models.LabYakInternal;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface LabYakMapper {
    LabYakMapper INSTANCE = Mappers.getMapper(LabYakMapper.class);

    LabYakRq toApiModel(LabYakInternal source);
    LabYakInternal toInternalModel(LabYakRq source);
}
