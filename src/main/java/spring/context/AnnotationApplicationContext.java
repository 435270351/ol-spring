package spring.context;

import com.service.HelloService;
import spring.factory.AbstractBeanFactory;
import spring.factory.AutowireCapableBeanFactory;
import spring.reader.AnnotationBeanDefinitionReader;

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
    private String[] locations;

    public AnnotationApplicationContext(String[] locations) {
        this(locations, new AutowireCapableBeanFactory());
    }

    public AnnotationApplicationContext(String[] locations, AbstractBeanFactory abstractBeanFactory) {
        super(abstractBeanFactory);
        this.locations = locations;
        this.refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory abstractBeanFactory) {
        // 交给AnnotationBeanDefinitionReader去加载我们添加的注解bean
        AnnotationBeanDefinitionReader annotationBeanDefinitionReader = new AnnotationBeanDefinitionReader(locations, abstractBeanFactory);
        annotationBeanDefinitionReader.loadBeanDefinitions();
    }

    public static void main(String[] args) throws Exception {
        String[] locations = { "com.service","com.aspect" };
        ApplicationContext applicationContext = new AnnotationApplicationContext(locations);
        HelloService service = (HelloService) applicationContext.getBean("HelloServiceImpl");

        service.say();
    }

}
