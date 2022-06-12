package com.xebia.yakshop.service


import com.xebia.yakshop.models.OrderInternal
import com.xebia.yakshop.models.StockInternal
import com.xebia.yakshop.storage.OrderHistoryStorageImpl
import spock.lang.Specification
import spock.lang.Subject

class OrderServiceTest extends Specification {

    @Subject
    private OrderService orderService = new OrderService(new OrderHistoryStorageImpl())

    def "should be able to place an order"() {
        given:
        def order = OrderInternal.builder()
                .stock(StockInternal.builder()
                        .milk(milkOrder)
                        .skins(skinOrder)
                        .build())
                .customer("YakMan")
                .build()

        when:
        def responseStock = orderService.placeOrder(order, stock, day)

        then:
        assert orderService.getOrderHistory().getSkinOrderHistory() == expectedSkins
        assert orderService.getOrderHistory().getMilkOrderHistory() == expectedMilk
        assert responseStock.getMilk() == expectedMilk
        assert responseStock.getSkins() == expectedSkins

        where:
        day | stock                                                | milkOrder | skinOrder | expectedMilk | expectedSkins
        1   | StockInternal.builder().milk(80.0).skins(2).build()  | 10.0      | 1         | 10.0         | 1
        2   | StockInternal.builder().milk(100.0).skins(1).build() | 100.0     | 1         | 100.0        | 1
        3   | StockInternal.builder().milk(200).skins(3).build()   | 3000.0    | 2         | 0.0          | 2

    }
}
