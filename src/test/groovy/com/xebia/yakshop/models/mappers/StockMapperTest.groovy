package com.xebia.yakshop.models.mappers

import com.xebia.yakshop.models.Stock
import com.xebia.yakshop.models.StockInternal
import spock.lang.Specification
import spock.lang.Subject

class StockMapperTest extends Specification {
    @Subject
    private final StockMapper mapper = new StockMapperImpl()

    def "test empty mappings"() {
        given:
        def stock = Stock.builder().build()
        def stockInternal = StockInternal.builder().build()

        when:
        def mapResultInternal = mapper.toInternalModel(stock)
        def mapResultApi = mapper.toApiModel(stockInternal)

        then:
        assert mapResultInternal == stockInternal
        assert mapResultApi == stock
    }

    def "Should correctly map Stock -> StockInternal"() {
        given:
        def stock = Stock.builder().skins(1).milk(1000.0).build()

        when:
        def mapResultInternal = mapper.toInternalModel(stock)

        then:
        assert !mapResultInternal.hasNoMilk()
        assert !mapResultInternal.hasNoSkins()
        assert mapResultInternal.getSkins() == stock.getSkins()
        assert mapResultInternal.getMilk() == stock.getMilk()
    }

    def "Should correctly map StockInternal -> Stock"() {
        given:
        def stockInternal = StockInternal.builder().skins(1).milk(1000.0).build()

        when:
        def mapResultApi = mapper.toApiModel(stockInternal)

        then:
        assert mapResultApi.getSkins() == stockInternal.getSkins()
        assert mapResultApi.getMilk() == stockInternal.getMilk()
    }
}
