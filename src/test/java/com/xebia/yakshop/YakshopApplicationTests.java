package com.xebia.yakshop;

import com.xebia.yakshop.service.YakShopController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {YakShopController.class})
class YakshopApplicationTests {

	@Test
	void contextLoads() {
	}

}
