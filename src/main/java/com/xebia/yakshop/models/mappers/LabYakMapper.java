package com.xebia.yakshop.models.mappers;

import com.xebia.api.models.LabYak;
import com.xebia.yakshop.models.LabYakInternal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LabYakMapper {
    LabYakMapper INSTANCE = Mappers.getMapper(LabYakMapper.class);

    LabYak toApiModel(LabYakInternal source);
    LabYakInternal toInternalModel(LabYak source);
}
