package com.dozenx.web.core.listener;

import com.dozenx.common.config.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:35 2019/6/19
 * @Modified By:
 */

@Component
public class MyQueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory
            .getLogger(MyQueueListener.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        SysConfig.PATH = contextRefreshedEvent.getApplicationContext().getEnvironment().getProperty("server.servlet.context-path");// /MerchantManage/

        System.out.println("项目启动成功");
    }
}

