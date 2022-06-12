package com.xebia.yakshop.service

import com.xebia.yakshop.models.LabYakInternal
import com.xebia.yakshop.models.OrderInternal
import com.xebia.yakshop.models.SexInternal
import com.xebia.yakshop.models.StockInternal
import com.xebia.yakshop.storage.HerdStorageImpl
import com.xebia.yakshop.storage.OrderHistoryStorageImpl
import spock.lang.Specification
import spock.lang.Subject

class OrderServiceTest extends Specification {

    private final List<LabYakInternal> herd = List.of(
            LabYakInternal.builder().age(4.0).name("Betty-1").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(8.0).name("Betty-2").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(9.5).name("Betty-3").sex(SexInternal.F).build())

    @Subject
    private OrderService orderService = new OrderService(new HerdStorageImpl(herd), new OrderHistoryStorageImpl())

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
        def responseStock = orderService.placeOrder(order, day)

        then:
        assert orderService.getOrderHistory().getSkinOrderHistory() == expectedSkins
        assert orderService.getOrderHistory().getMilkOrderHistory() == expectedMilk
        assert responseStock.getMilk() == expectedMilk
        assert responseStock.getSkins() == expectedSkins

        where:
        day | milkOrder | skinOrder | expectedMilk | expectedSkins
        1   | 10.0      | 1         | 10.0         | 1
        2   | 100.0     | 1         | 100.0        | 1
        3   | 3000.0    | 2         | 0.0          | 2

    }

    def "should be able to place multiple orders"() {
        given:
        def order1 = OrderInternal.builder()
                .stock(StockInternal.builder()
                        .milk(10.0)
                        .skins(1)
                        .build())
                .customer("YakMan")
                .build()

        def order2 = OrderInternal.builder()
                .stock(StockInternal.builder()
                        .milk(200.0)
                        .skins(3)
                        .build())
                .customer("YakMan")
                .build()


        when:
        def response1 = orderService.placeOrder(order1, 1)
        def response2 = orderService.placeOrder(order2, 1)

        then:
        assert response1.getSkins() == 1
        assert response2.getSkins() == 0
        assert response1.getMilk() == 10.0
        assert response2.getMilk() == 0.0
    }
}
