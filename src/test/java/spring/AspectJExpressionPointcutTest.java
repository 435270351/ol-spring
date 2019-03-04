//package spring;
//
//import demo.Demo;
//import demo.ThreadDemo;
//import org.testng.Assert;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import service.EnService;
//import service.EnServiceImpl;
//import spring.aop.AspectJExpressionPointcut;
//
//import java.lang.reflect.Method;
//
///**
// * （描述）
// *
// * @author tangzw
// * @date 2019-03-03
// * @since (版本)
// */
//@Test
//public class AspectJExpressionPointcutTest {
//
////    @DataProvider(name = "targetClassData")
////    public Object[][] targetClassData(){
////
////        Object[][] arr = new Object[][]{{ EnService.class },{ EnServiceImpl.class },{ ThreadDemo.class }};
////
////        return arr;
////    }
////
////    @Test(dataProvider = "targetClassData")
////    public void targetClassTest(Class clazz){
////        String expression = "execution(* service.*.*(**))";
////        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
////        aspectJExpressionPointcut.setExpression(expression);
////
////        boolean flag = aspectJExpressionPointcut.matches(clazz);
////        System.out.println(flag);
////        Assert.assertTrue(flag);
////    }
//
//    @DataProvider(name = "methodData")
//    public Object[][] methodData(){
//
//        Object[][] arr = new Object[][]{{ EnService.class.getDeclaredMethods() },{ EnServiceImpl.class.getDeclaredMethods() },{ ThreadDemo.class.getDeclaredMethods() }};
//
//        return arr;
//    }
//
//    @Test(dataProvider = "methodData")
//    public void methodTest(Method[] methods){
//        String expression = "execution(* service.*.*(..))";
//        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
//        aspectJExpressionPointcut.setExpression(expression);
//
//        for (Method method:methods){
//            boolean flag = aspectJExpressionPointcut.matches(method);
//            System.out.println(flag);
//            Assert.assertTrue(flag);
//        }
//
//    }
//}
