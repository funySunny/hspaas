package com.huashi.web;

import com.huashi.common.util.LogUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HsWebApplication {

//	@Override
//	protected SpringApplicationBuilder configure(
//			SpringApplicationBuilder application) {
//		return application.sources(HsWebApplication.class);
//	}

    public static void main(String[] args) {
        SpringApplication.run(HsWebApplication.class, args);
        LogUtils.info("----------------华时融合Web平台已启动-----------------");
    }
}
