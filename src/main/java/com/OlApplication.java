package com;

import com.service.EnServiceImpl;
import com.service.HelloService;
import spring.ioc.context.AnnotationApplicationContext;
import spring.ioc.context.ApplicationContext;

/**
 * 测试类
 *
 * @author tangzw
 * @date 2019-04-10
 * @since 1.0.0
 */
public class OlApplication {

    public static void main(String[] args) throws Exception {
        String[] locations = { "com" };
        ApplicationContext applicationContext = new AnnotationApplicationContext(locations);

        EnServiceImpl service = (EnServiceImpl) applicationContext.getBean("EnServiceImpl");
        service.ha();
    }
}
