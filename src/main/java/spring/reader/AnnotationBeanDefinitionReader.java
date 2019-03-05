package spring.reader;

import com.service.HelloServiceImpl;
import spring.annotation.After;
import spring.annotation.Around;
import spring.annotation.Aspect;
import spring.annotation.Before;
import spring.annotation.PointCut;
import spring.annotation.Resource;
import spring.annotation.Service;
import spring.aop.AdvisedSupport;
import spring.aop.AopMethod;
import spring.aop.AspectJExpressionPointcut;
import spring.aop.ClassMatcher;
import spring.bean.BeanDefinition;
import spring.bean.BeanReference;
import spring.bean.PropertyValue;
import spring.enums.AdviceEnum;
import spring.factory.AbstractBeanFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注解加载
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public AnnotationBeanDefinitionReader(String[] locations, AbstractBeanFactory abstractBeanFactory) {
        super(locations, abstractBeanFactory);
    }

    @Override
    public void loadBeanDefinitions() {
        for (String location : locations) {
            doLoadBeanDefinitions(location);
        }
    }

    protected void doLoadBeanDefinitions(String location) {
        System.out.println(location);
        File file = new File(location);

        System.out.println(file.exists());
        // 如果不是文件夹
        if (!file.isDirectory()) {
            return;
        }

        File[] files = file.listFiles();
        List<String> pathList = new ArrayList<>();

        // 将文件路径转为可用的类路径
        // E:/work/JavaWorkSpace/ol-spring/target/classes/service/xx/xx.class 转为 xx.xx
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            String startStr = "classes\\";
            String endStr = ".class";
            int startIndex = absolutePath.indexOf(startStr) + startStr.length();
            int endIndex = absolutePath.indexOf(endStr);

            // 去掉多余的地址路径，留下xx/xx
            String path = absolutePath.substring(startIndex, endIndex);
            // 将xx/xx转为xx.xx
            String classPath = path.replace('\\', '.');

            pathList.add(classPath);
        }

        System.out.println(pathList);

        registerBeanDefinition(pathList);
    }

    protected void registerBeanDefinition(List<String> pathList) {
        for (String beanClass : pathList) {
            try {
                Class clazz = Class.forName(beanClass);

                if (clazz.isAnnotationPresent(Service.class)) {
                    // Service注解装配
                    registerServiceBeanDefinition(clazz, beanClass);

                } else if (clazz.isAnnotationPresent(Aspect.class)) {
                    // Aspect注解装配
                    registerAspectBeanDefinition(clazz);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void registerServiceBeanDefinition(Class clazz, String beanClass) {

        BeanDefinition beanDefinition = new BeanDefinition();

        // 获取所有的属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 添加了ResourceAspect的属性，需要被注入
            if (field.isAnnotationPresent(Resource.class)) {
                BeanReference beanReference = new BeanReference();

                // 属性名
                String fieldName = field.getName();
                // 类型的类路径
                String typeBeanClass = field.getType().getName();

                Resource resource = field.getAnnotation(Resource.class);
                String beanName = resource.value();

                // 根据name注入
                beanReference.setBeanName(beanName);
                // 根据type注入
                beanReference.setBeanClass(typeBeanClass);

                beanDefinition.getPropertyValueList().add(new PropertyValue(fieldName, beanReference));
            }
        }

        beanDefinition.setBeanClass(beanClass);

        abstractBeanFactory.getBeanDefinitionMap().put(clazz.getSimpleName().toLowerCase(), beanDefinition);

    }

    private void registerAspectBeanDefinition(Class clazz) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            Method[] methods = clazz.getDeclaredMethods();
            Object target = clazz.newInstance();

            AdvisedSupport advisedSupport = abstractBeanFactory.getAdvisedSupport();

            Map<String, ClassMatcher> pointCutMap = new HashMap<>();
            //        Map<String, String> pointCutMap = new HashMap<>();

            // 切点装配
            for (Field field : fields) {
                PointCut pointCut = field.getDeclaredAnnotation(PointCut.class);
                if (pointCut != null) {
                    String expression = pointCut.value();
                    AspectJExpressionPointcut aspectPointCut = new AspectJExpressionPointcut();
                    aspectPointCut.setExpression(expression);

                    pointCutMap.put(field.getName(), aspectPointCut);
                    advisedSupport.addClassMatcher(aspectPointCut);
                }
            }

            // 通知装配
            for (Method method : methods) {
                AdviceEnum adviceEnum = null;
                ClassMatcher classMatcher = null;

                // 前置通知
                if (method.isAnnotationPresent(Before.class)) {
                    Before before = method.getDeclaredAnnotation(Before.class);
                    classMatcher = pointCutMap.get(before.value());

                    if (classMatcher == null) {
                        System.out.println("没有该切点：" + before.value());
                        continue;
                    }

                    adviceEnum = AdviceEnum.BEFORE;

                } else if (method.isAnnotationPresent(Around.class)) {
                    Around around = method.getDeclaredAnnotation(Around.class);
                    classMatcher = pointCutMap.get(around.value());

                    if (classMatcher == null) {
                        System.out.println("没有该切点：" + around.value());
                        continue;
                    }

                    adviceEnum = AdviceEnum.AROUND;
                } else if (method.isAnnotationPresent(After.class)) {
                    After after = method.getDeclaredAnnotation(After.class);
                    classMatcher = pointCutMap.get(after.value());

                    if (classMatcher == null) {
                        System.out.println("没有该切点：" + after.value());
                        continue;
                    }

                    adviceEnum = AdviceEnum.AFTER;
                } else {
                    continue;
                }

                AopMethod aopMethod = new AopMethod();
                aopMethod.setMethod(method);
                aopMethod.setTarget(target);
                aopMethod.setClassMatcher(classMatcher);

                advisedSupport.addAopMethod(adviceEnum, aopMethod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        //        String str = "service";
        //
        //        BeanDefinitionReader beanDefinitionReader = new AnnotationBeanDefinitionReader(str);
        //        beanDefinitionReader.loadBeanDefinitions();
        //
        //        Class clazz = HelloServiceImpl.class;
        //        System.out.println(clazz.getDeclaredFields().length);

    }
}
