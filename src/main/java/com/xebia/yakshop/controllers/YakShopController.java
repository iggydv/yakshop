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
import com.xebia.yakshop.service.HerdService;
import com.xebia.yakshop.service.OrderService;
import com.xebia.yakshop.storage.HerdStorage;
import com.xebia.yakshop.storage.HerdStorageImpl;
import lombok.extern.log4j.Log4j2;
import org.openapitools.api.YakShopApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@Log4j2
public class YakShopController implements YakShopApi {

    private HerdService herdService;
    private final OrderService orderService;

    @Autowired
    public YakShopController(HerdService herd, OrderService orderService) {
        this.herdService = herd;
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Void> loadHerd(List<LabYakRq> herdRq) {
        try {
            HerdStorage herd = HerdMapper.getInstance().toInternalModel(herdRq);
            herdService.loadHerd(herd);
            log.info("New herd loaded: {}", herd);
        } catch (Exception e) {
            log.error("Invalid load herd request: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @Override
    public ResponseEntity<HerdStatus> getHerdStatus(Integer T) {
        HerdStatus herdStatus = HerdStatus.builder().build();
        try {
            this.herdService.calculateAgeLastShaved(T - 1);
            herdStatus = HerdMapper.getInstance().toHerdStatus(herdService.getHerdStorage(), T);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Herd status calculated successfully");
        return ResponseEntity.ok(herdStatus);
    }

    @Override
    public ResponseEntity<Stock> getStock(Integer T) {
        log.info("StockInternal successfully calculated");
        final StockInternal stockInternal = this.herdService.calculateStock(T);
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> rootResponse() {
        String welcomeMessage = "Welcome to YakShop - Currently the site is still under construction. \nFeel free to explore our API at http://localhost:8080/swagger-ui/index.html#/";
        return new ResponseEntity<>(welcomeMessage, HttpStatus.OK);
    }
}
