package com;

import com.service.HelloService;
import spring.ioc.context.AnnotationApplicationContext;
import spring.ioc.context.ApplicationContext;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-10
 * @since (版本)
 */
public class OlApplication {

    public static void main(String[] args) throws Exception {
        String[] locations = { "com.service" };
        ApplicationContext applicationContext = new AnnotationApplicationContext(locations);
        HelloService service = (HelloService) applicationContext.getBean("HelloServiceImpl");

        service.say();
    }
}
