package com.service;

import spring.annotation.Service;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-26
 * @since (版本)
 */
@Service
public class EnServiceImpl implements EnService {

    @Override
    public void ha() {
        System.out.println("ha");
    }

}
