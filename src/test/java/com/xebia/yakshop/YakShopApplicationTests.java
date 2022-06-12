package com.xebia.yakshop;

import com.xebia.yakshop.controllers.YakShopController;
import com.xebia.yakshop.service.HerdService;
import com.xebia.yakshop.service.OrderService;
import com.xebia.yakshop.storage.HerdStorageImpl;
import com.xebia.yakshop.storage.OrderHistoryStorageImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {YakShopController.class, HerdService.class, OrderService.class, HerdStorageImpl.class, OrderHistoryStorageImpl.class})
class YakShopApplicationTests {

	@Test
	void contextLoads() {
	}

}
