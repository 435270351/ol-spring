package demo;

import org.testng.annotations.Test;
import service.HelloService;
import spring.context.AnnotationApplicationContext;
import spring.context.ApplicationContext;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-27
 * @since (版本)
 */
@Test
public class ApplicationContextTest {

    @Test
    public void getBean() throws Exception {
        ApplicationContext applicationContext = new AnnotationApplicationContext("service");
        HelloService helloService = (HelloService) applicationContext.getBean("HelloServiceImpl");

        helloService.say();

    }
}
