package com.example.demo;

import org.springframework.stereotype.Component;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:44 2019/7/30
 * @Modified By:
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }

    @Override
    public String user(String name) {
        return "sorry user "+name;
    }
}
