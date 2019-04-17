package spring.ioc.reader;

import org.apache.commons.lang.StringUtils;
import spring.common.annotation.Aspect;
import spring.common.annotation.Resource;
import spring.common.annotation.Scope;
import spring.common.annotation.Service;
import spring.common.enums.AnnotationEnum;
import spring.common.enums.ScopeEnum;
import spring.ioc.bean.BeanDefinition;
import spring.ioc.bean.BeanReference;
import spring.ioc.bean.PropertyValue;
import spring.ioc.factory.DefaultListableBeanFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解解析类
 * 负责解析包下添加了注解的方法
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public AnnotationBeanDefinitionReader(DefaultListableBeanFactory defaultListableBeanFactory) {
        super(defaultListableBeanFactory);
    }

    @Override
    public void loadBeanDefinitions(String[] locations) {
        // 解析路劲
        for (String location : locations) {
            doLoadBeanDefinitions(location);
        }

    }

    protected void doLoadBeanDefinitions(String location) {
        // 获取location包下所有的类路径
        List<String> classPathList = getClassPathList(location);

        // 将添加了注解的bean注册到BeanDefinitionRegistry上
        registerBeanDefinition(classPathList);
    }

    /**
     * 获取location包下所有的类路径
     *
     * @author:tangzw
     * @date: 2019-04-11
     * @since v1.0.0
     * @param location
     * @return
     */
    private List<String> getClassPathList(String location) {
        // 将xxx.yy转为xxx/yy
        location = location.replace('.', '/');
        // 获取绝对地址
        location = this.getClass().getResource("/").getPath() + location;

        File file = new File(location);
        if (!file.isDirectory()) {
            return new ArrayList<>();
        }

        return doGetClassPathList(file);
    }

    public List<String> doGetClassPathList(File file) {
        File[] files = file.listFiles();
        List<String> classPathList = new ArrayList<>();

        if (files.length == 0) {
            return classPathList;
        }

        for (File item : files) {

            if (item.isDirectory()) {
                // 如果是包，则继续进行扫描
                classPathList.addAll(doGetClassPathList(item));
                continue;
            }

            // 获取绝对路径
            String absolutePath = item.getAbsolutePath();

            String startStr = "classes\\";
            String endStr = ".class";
            int startIndex = absolutePath.indexOf(startStr) + startStr.length();
            int endIndex = absolutePath.indexOf(endStr);

            // 去掉多余的路径，留下xx/yy
            String classPath = absolutePath.substring(startIndex, endIndex);
            // 将xx/yy转为xx.yy
            classPath = classPath.replace('\\', '.');

            classPathList.add(classPath);
        }

        return classPathList;
    }

    /**
     * 将添加了注解的bean注册到BeanDefinitionRegistry上
     *
     * @author:tangzw
     * @date: 2019-04-11
     * @since v1.0.0
     * @param classPathList
     */
    public void registerBeanDefinition(List<String> classPathList) {
        for (String classPath : classPathList) {
            try {
                Class clazz = Class.forName(classPath);

                if (clazz.isAnnotationPresent(Service.class)) {
                    // Service注解装配
                    registerServiceBeanDefinition(clazz, classPath);

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

        // 单例还是原型模式
        Scope scope = (Scope) clazz.getAnnotation(Scope.class);

        if (scope != null) {
            String scopeStr = scope.value();
            if (!scopeStr.equals("") && ScopeEnum.parse(scopeStr) == null) {
                throw new RuntimeException("No Scope registered for scope name " + scopeStr);
            }

            beanDefinition.setScope(scopeStr);
        }

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
                beanName = StringUtils.isEmpty(beanName) ? field.getName() : beanName;

                // name注入
                beanReference.setBeanName(beanName);
                // type注入
                beanReference.setBeanClass(typeBeanClass);

                beanDefinition.getPropertyValueList().add(new PropertyValue(fieldName, beanReference));
            }
        }

        beanDefinition.setBeanClass(clazz);
        beanDefinition.setAnnotation(AnnotationEnum.Service.name());

        defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName().toLowerCase(), beanDefinition);

    }

    private void registerAspectBeanDefinition(Class clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setAnnotation(AnnotationEnum.Aspect.name());

        defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName().toLowerCase(), beanDefinition);

    }

}
