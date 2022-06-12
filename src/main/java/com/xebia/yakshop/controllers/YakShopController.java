package com.xebia.yakshop.controllers;

import com.xebia.yakshop.models.HerdStatus;
import com.xebia.yakshop.models.LabYakRq;
import com.xebia.yakshop.models.Order;
import com.xebia.yakshop.models.OrderInternal;
import com.xebia.yakshop.models.Stock;
import com.xebia.yakshop.models.StockInternal;
import com.xebia.yakshop.models.mappers.HerdMapper;
import com.xebia.yakshop.models.mappers.OrderMapper;
import com.xebia.yakshop.models.mappers.StockMapper;
import com.xebia.yakshop.service.OrderService;
import com.xebia.yakshop.storage.HerdStorageImpl;
import lombok.extern.log4j.Log4j2;
import org.openapitools.api.YakShopApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Log4j2
public class YakShopController implements YakShopApi {

    private HerdStorageImpl herd;
    private final OrderService orderService;
    private final HerdMapper herdMapper = new HerdMapper();

    @Autowired
    public YakShopController(HerdStorageImpl herd, OrderService orderService) {
        this.herd = herd;
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Void> loadHerd(List<LabYakRq> herdRq) {
        try {
            this.herd = herdMapper.toInternalModel(herdRq);
            orderService.setHerd(herd);
            log.info("New herd loaded: {}", herd);
        } catch (Exception e) {
            log.error("Invalid load herd request: {} - {}", herd, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @Override
    public ResponseEntity<HerdStatus> getHerdStatus(Integer T) {
        HerdStatus herdStatus = HerdStatus.builder().build();
        try {
            this.herd.calculateAgeLastShaved(T - 1);
            herdStatus = herdMapper.toHerdStatus(herd, T);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Herd status calculated successfully");
        return ResponseEntity.ok(herdStatus);
    }

    @Override
    public ResponseEntity<Stock> getStock(Integer T) {
        log.info("StockInternal successfully calculated");
        final StockInternal stockInternal = this.herd.calculateStock(T);
        Stock stockRs = StockMapper.INSTANCE.toApiModel(stockInternal);
        log.info("StockInternal successfully calculated");
        return ResponseEntity.ok(stockRs);
    }

    @Override
    public ResponseEntity<Stock> order(Integer T, Order order) {
        OrderInternal orderInternal = OrderMapper.INSTANCE.toInternalModel(order);
        StockInternal availableItems = this.orderService.placeOrder(orderInternal, T);
        Stock response = StockMapper.INSTANCE.toApiModel(availableItems);

        if (availableItems.emptyOrder()) {
            log.info("No oder items are available - order not processed");
            return ResponseEntity.notFound().build();
        } else if (availableItems.hasNoMilk() && order.getStock().getMilk() != null ||
                availableItems.hasNoSkins() && order.getStock().getSkins() != null) {
            log.info("Partial order processed successfully!");
            return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT);
        }
        log.info("Order processed successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
