package com.service;

import spring.common.annotation.Resource;
import spring.common.annotation.Service;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-01-30
 * @since (版本)
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Resource
    public EnService enService;

    public void say() {
        System.out.println("Hellos");
        enService.ha();
    }

    public EnService getEnService() {
        return enService;
    }

    public void setEnService(EnService enService) {
        this.enService = enService;
    }
}
