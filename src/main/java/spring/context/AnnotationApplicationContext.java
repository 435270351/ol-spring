package spring.context;

import spring.bean.BeanDefinition;
import spring.factory.AbstractBeanFactory;
import spring.factory.AutowireCapableBeanFactory;
import spring.reader.AnnotationBeanDefinitionReader;

import java.util.Map;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-26
 * @since (版本)
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

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
        AnnotationBeanDefinitionReader annotationBeanDefinitionReader = new AnnotationBeanDefinitionReader(location);
        annotationBeanDefinitionReader.loadBeanDefinitions();

        Map<String, BeanDefinition> register = annotationBeanDefinitionReader.getRegister();
        for (String key : register.keySet()) {
            abstractBeanFactory.getBeanDefinitionMap().put(key, register.get(key));
        }
    }

}
