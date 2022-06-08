package com.xebia.yakshop.api;

import com.xebia.api.models.Herd;
import com.xebia.yakshop.models.HerdInternal;
import com.xebia.yakshop.models.mappers.HerdMapper;
import org.openapitools.api.YakShopApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class YakShopController implements YakShopApi {
    @Override
    public ResponseEntity<Void> loadHerd(Herd herd) {

        HerdInternal herdInternal = HerdMapper.INSTANCE.toInternalModel(herd);
        System.out.println(herdInternal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
