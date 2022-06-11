package com.xebia.yakshop.service;

import com.xebia.yakshop.models.HerdInternal;
import com.xebia.yakshop.models.HerdStatus;
import com.xebia.yakshop.models.LabYakRq;
import com.xebia.yakshop.models.StockRs;
import com.xebia.yakshop.models.mappers.HerdMapper;
import lombok.extern.log4j.Log4j2;
import org.openapitools.api.ApiUtil;
import org.openapitools.api.YakShopApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Log4j2
public class YakShopController implements YakShopApi {
    private HerdInternal herd;
    private final HerdMapper herdMapper = new HerdMapper();

    @Override
    public ResponseEntity<Void> loadHerd(List<LabYakRq> herdRq) {
        try {
            this.herd = herdMapper.toInternalModel(herdRq);
            log.info("New herd loaded: {}", herd);
        } catch (Exception e) {
            log.error("Invalid load herd request: {}", herd);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @Override
    public ResponseEntity<HerdStatus> getHerdStatus(Integer T) {
        HerdStatus herdStatus = HerdStatus.builder().build();
        try {
            this.herd.calculateAgeLastShaved(T-1);
            herdStatus = herdMapper.toHerdStatus(herd, T);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Herd status calculated successfully");
        return ResponseEntity.ok(herdStatus);
    }

    @Override
    public ResponseEntity<StockRs> getStock(Integer T) {
        log.info("Stock successfully calculated");
        final double milk = this.herd.calculateTotalMilkForPeriod(T);
        final int skins = this.herd.calculateTotalSkinsForPeriod(T);
        StockRs stockRs = StockRs.builder().milk(milk).skins(skins).build();
        log.info("Stock successfully calculated");
        return ResponseEntity.ok(stockRs);
    }
}
