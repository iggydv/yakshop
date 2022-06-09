package com.xebia.yakshop.service;

import com.xebia.api.models.HerdRq;
import com.xebia.yakshop.models.HerdInternal;
import com.xebia.yakshop.models.mappers.HerdMapper;
import org.openapitools.api.YakShopApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class YakShopController implements YakShopApi {
    private HerdInternal herd;

    @Override
    public ResponseEntity<Void> loadHerd(HerdRq herdRq) {

        herd = HerdMapper.INSTANCE.toInternalModel(herdRq);
        System.out.println(herd);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
