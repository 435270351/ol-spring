package com.service;

import spring.common.annotation.Resource;
import spring.common.annotation.Service;

@Service
public class EnServiceImpl {

    @Resource
    HelloService helloService;

    public void ha() {
        System.out.println("ha");
        helloService.say();
    }



}
