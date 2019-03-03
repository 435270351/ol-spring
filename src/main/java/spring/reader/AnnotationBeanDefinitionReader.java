package spring.reader;

import service.HelloServiceImpl;
import spring.aspect.ResourceAspect;
import spring.aspect.ServiceAspect;
import spring.bean.BeanDefinition;
import spring.bean.BeanReference;
import spring.bean.PropertyValue;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解加载
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public AnnotationBeanDefinitionReader(String location) {
        super(location);
    }

    @Override
    public void loadBeanDefinitions() {
        String location = getLocation();
        doLoadBeanDefinitions(location);
    }

    protected void doLoadBeanDefinitions(String location){
        System.out.println(location);
        File file = new File(getLocation());

        System.out.println(file.exists());
        // 如果不是文件夹
        if (!file.isDirectory()){
            return;
        }

        File[] files = file.listFiles();
        List<String> pathList = new ArrayList<>();

        // 将文件路径转为可用的类路径
        // E:/work/JavaWorkSpace/ol-spring/target/classes/service/xx/xx.class 转为 xx.xx
        for (File f:files){
            String absolutePath = f.getAbsolutePath();
            String startStr = "classes\\";
            String endStr = ".class";
            int startIndex = absolutePath.indexOf(startStr)+startStr.length();
            int endIndex = absolutePath.indexOf(endStr);

            // 去掉多余的地址路径，留下xx/xx
            String path = absolutePath.substring(startIndex,endIndex);
            // 将xx/xx转为xx.xx
            String classPath = path.replace('\\','.');

            pathList.add(classPath);
        }

        System.out.println(pathList);

        registerBeanDefinition(pathList);
    }

    protected void registerBeanDefinition(List<String> pathList){
        for (String beanClass:pathList){
            try {
                Class clazz = Class.forName(beanClass);

                // 添加了ServiceAspect注解才能被加载
                if (clazz.isAnnotationPresent(ServiceAspect.class)){
                    BeanDefinition beanDefinition = new BeanDefinition();

                    // 获取所有的属性
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field:fields){
                        // 添加了ResourceAspect的属性，需要被注入
                        if (field.isAnnotationPresent(ResourceAspect.class)){
                            BeanReference beanReference = new BeanReference();

                            // 属性名
                            String fieldName = field.getName();
                            // 类型的类路径
                            String typeBeanClass = field.getType().getName();

                            ResourceAspect resourceAspect = field.getAnnotation(ResourceAspect.class);
                            String beanName = resourceAspect.name();

                            // 根据name注入
                            beanReference.setBeanName(beanName);
                            // 根据type注入
                            beanReference.setBeanClass(typeBeanClass);

                            beanDefinition.getPropertyValueList().add(new PropertyValue(fieldName,beanReference));
                        }
                    }

                    beanDefinition.setBeanClass(beanClass);

                    getRegister().put(clazz.getSimpleName(),beanDefinition);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(getRegister().toString());
    }

    public static void main(String[] args) throws Exception {
        String str = "service";

        BeanDefinitionReader beanDefinitionReader  = new AnnotationBeanDefinitionReader(str);
        beanDefinitionReader.loadBeanDefinitions();

        Class clazz = HelloServiceImpl.class;
        System.out.println(clazz.getDeclaredFields().length);

    }
}
