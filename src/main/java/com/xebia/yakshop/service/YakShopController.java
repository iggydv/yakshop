package com.xebia.yakshop.service;

//import com.xebia.api.models.Blob;
//import com.xebia.api.models.Blobs;
//import com.xebia.api.models.HerdRq;
import com.xebia.api.models.LabYakRq;
import com.xebia.yakshop.models.HerdInternal;
import com.xebia.yakshop.models.mappers.HerdMapper;
import com.xebia.yakshop.models.mappers.LabYakMapper;
import lombok.extern.log4j.Log4j2;
import org.openapitools.api.YakShopApi;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@Log4j2
public class YakShopController implements YakShopApi {
    private HerdInternal herd;

    @Override
    public ResponseEntity<Void> loadHerd(List<LabYakRq> herdRq) {
        try {
            herd = HerdInternal.builder().herd(herdRq.stream().map(LabYakMapper.INSTANCE::toInternalModel).collect(Collectors.toList())).build();
            log.info("New herd loaded: {}", herd);
        } catch (Exception e) {
            log.error("Invalid load herd request: {}", herd);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }
}
