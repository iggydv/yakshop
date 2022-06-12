package com.xebia.yakshop.models.mappers;

import com.xebia.yakshop.models.Stock;
import com.xebia.yakshop.models.StockInternal;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface StockMapper {
    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    default Stock toApiModel(StockInternal from) {
        if ( from == null ) {
            return null;
        }

        Stock.StockBuilder stock = Stock.builder();

        if (from.getMilk() != 0.0) {
            stock.milk( from.getMilk() );
        }

        if (from.getSkins() != 0) {
            stock.skins( from.getSkins() );
        }


        return stock.build();
    }
    StockInternal toInternalModel(Stock from);
}
