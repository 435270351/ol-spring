package spring.ioc.context;

import spring.ioc.factory.DefaultListableBeanFactory;
import spring.ioc.reader.AnnotationBeanDefinitionReader;

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
        this.locations = locations;
        this.refresh();
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory) {
        // 交给AnnotationBeanDefinitionReader去解析bean
        AnnotationBeanDefinitionReader reader = new AnnotationBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions(locations);
    }

}
