package com.service;

import spring.common.annotation.Resource;
import spring.common.annotation.Service;

@Service
public class HelloServiceImpl implements HelloService {

    public void say() {
        System.out.println("Hellos");
        say2();
    }

    public void say2(){
        System.out.println("22");
    }
}
