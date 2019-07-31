package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 10:54 2019/7/30
 * @Modified By:
 */
@Service
public class HelloService {


    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")//如果出错了会去找hiError方法
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }


}
