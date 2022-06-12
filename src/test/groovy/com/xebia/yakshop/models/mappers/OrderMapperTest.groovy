package com.xebia.yakshop.models.mappers

import com.xebia.yakshop.models.Order
import com.xebia.yakshop.models.Stock
import spock.lang.Specification
import spock.lang.Subject

class OrderMapperTest extends Specification {
    @Subject
    private final OrderMapper mapper = new OrderMapperImpl();

    def "should correctly map an empty object"() {
        given:
        def order = Order.builder().build()

        when:
        def result = mapper.toInternalModel(order)

        then:
        assert result.getCustomer() == null
        assert result.getStock() == null
    }

    def "should correctly map Order -> OrderInternal"() {
        given:
        def order = Order.builder().customer("jack").stock(Stock.builder().milk(10.0).skins(3).build()).build()

        when:
        def result = mapper.toInternalModel(order)

        then:
        assert result.getCustomer() == order.getCustomer()
        assert result.getStock().getMilk() == order.getStock().getMilk()
        assert result.getStock().getSkins() == order.getStock().getSkins()

    }
}
