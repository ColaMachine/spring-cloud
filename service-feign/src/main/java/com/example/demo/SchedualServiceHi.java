package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:25 2019/7/30
 * @Modified By:
 */
@FeignClient(value = "service-hi" ,fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);


    @RequestMapping(value = "/user",method = RequestMethod.GET)
    String user(@RequestParam(value = "name") String name);

}