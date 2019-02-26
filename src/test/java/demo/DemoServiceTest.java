package demo;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-21
 * @since (版本)
 */
public class DemoServiceTest {

    DemoService demoService = new DemoService();

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider(Method method){

        if (method.getName().equals("getName")){
            return new Object[][] {{"姓名"}};
        }

        if (method.getName().equals("getAge")){
            return new Object[][] {{"年龄"}};
        }

        return new Object[][]{};
    }

    @Test(dataProvider = "dataProvider")
    public void getName(String name){
        String result = demoService.getName();
        System.out.println(name);

        Assert.assertEquals(result,"ling");
    }

    @Test(dataProvider = "dataProvider")
    public void getAge(String name){
        int result = demoService.getAge();
        System.out.println(name);

        Assert.assertEquals(result,3);
    }
}
