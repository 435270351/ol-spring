package demo;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-21
 * @since (版本)
 */
@Test
public class HelloServiceTest {

    HelloService helloService = new HelloService();

    @Test(groups = "test1")
    public void say(){
        String result = helloService.say();

        Assert.assertEquals(result,"hh");
        Assert.assertEquals(result,"hh");
    }

    @Test(groups = "test2")
    public void hello() throws InterruptedException {
        String result = helloService.hello();
        Thread.sleep(2000);
        Assert.assertEquals(result,"lala");
    }

}
