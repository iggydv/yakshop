package com.xebia.yakshop.models.mappers;

import com.xebia.yakshop.models.LabYakInternal;
import com.xebia.yakshop.models.LabYakRq;
import com.xebia.yakshop.models.LabYakRs;
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

    default LabYakRs toApiResponseModel(LabYakInternal source, int T) {
        if ( source == null ) {
            return null;
        }

        LabYakRs.LabYakRsBuilder labYakRs = LabYakRs.builder();

        labYakRs.name( source.getName() );
        labYakRs.age( source.getAge() + T/100.00 );
        labYakRs.ageLastShaved( source.getAgeLastShaved() );

        return labYakRs.build();
    }
}
