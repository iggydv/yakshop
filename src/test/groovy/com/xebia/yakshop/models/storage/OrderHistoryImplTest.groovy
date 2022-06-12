package com.xebia.yakshop.models.storage


import com.xebia.yakshop.models.StockInternal
import com.xebia.yakshop.storage.OrderHistory
import com.xebia.yakshop.storage.OrderHistoryImpl
import spock.lang.Specification
import spock.lang.Subject

class OrderHistoryImplTest extends Specification {
    @Subject
    private final OrderHistory orderHistory = new OrderHistoryImpl()

    def setup() {
        orderHistory.reset()
    }

    def "should successfully retrieve skin stock history with empty history"() {
        when:
        def total = orderHistory.getSkinOrderHistory()

        then:
        assert total == 0
    }

    def "should successfully retrieve milk stock history with empty history"() {
        when:
        def total = orderHistory.getMilkOrderHistory()

        then:
        assert total == 0.0
    }

    def "should successfully retrieve stock history"() {
        given:
        orderHistory.addOrder("jack", StockInternal.builder().milk(100.00).skins(1).build())
        orderHistory.addOrder("sam", StockInternal.builder().milk(200.00).skins(2).build())
        orderHistory.addOrder("gloria", StockInternal.builder().milk(300.00).skins(1).build())
        orderHistory.addOrder("susan", StockInternal.builder().milk(400.00).skins(3).build())

        when:
        def totalMilk = orderHistory.getMilkOrderHistory()
        def totalSkins = orderHistory.getSkinOrderHistory()

        then:
        assert totalMilk == 1000.0
        assert totalSkins == 7
    }

    def "should be able to add an order to the order history"() {
        given:
        orderHistory.addOrder("jack", StockInternal.builder().milk(100.00).skins(1).build())
        orderHistory.addOrder("sam", StockInternal.builder().milk(200.00).skins(2).build())
        orderHistory.addOrder("gloria", StockInternal.builder().milk(300.00).skins(1).build())
        orderHistory.addOrder("susan", StockInternal.builder().milk(400.00).skins(3).build())

        when:
        orderHistory.addOrder("susan", StockInternal.builder().milk(400.00).skins(3).build())
        System.out.println(orderHistory.getOrders().values())
        def keySetSize = orderHistory.getOrders().keySet().size()
        def susanValuesSize = orderHistory.getOrders().get("susan").size()

        then:
        assert keySetSize == 4
        assert susanValuesSize == 2
    }
}
