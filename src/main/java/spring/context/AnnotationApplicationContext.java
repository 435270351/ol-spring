package spring.context;


import com.service.HelloService;
import spring.bean.BeanDefinition;
import spring.factory.AbstractBeanFactory;
import spring.factory.AutowireCapableBeanFactory;
import spring.reader.AnnotationBeanDefinitionReader;

import java.util.Map;

/**
 * 注解类加载
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    /**
     * 需要加载的包路径
     */
    private String location;

    public AnnotationApplicationContext(String location) {
        this(location, new AutowireCapableBeanFactory());
    }

    public AnnotationApplicationContext(String location, AbstractBeanFactory abstractBeanFactory) {
        super(abstractBeanFactory);
        this.location = location;
        this.refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory abstractBeanFactory) {
        // 交给AnnotationBeanDefinitionReader去加载我们添加的注解bean
        AnnotationBeanDefinitionReader annotationBeanDefinitionReader = new AnnotationBeanDefinitionReader(location);
        annotationBeanDefinitionReader.loadBeanDefinitions();

        // 获取到BeanDefinition关系需要添加到AbstractBeanFactory的beanDefinitionMap里
        Map<String, BeanDefinition> register = annotationBeanDefinitionReader.getRegister();
        for (String key : register.keySet()) {
            abstractBeanFactory.getBeanDefinitionMap().put(key.toLowerCase(), register.get(key));
        }
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationApplicationContext("com.service");
        HelloService service = (HelloService) applicationContext.getBean("HelloServiceImpl");

        service.say();
    }

}
