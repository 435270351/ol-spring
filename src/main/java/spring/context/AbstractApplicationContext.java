package spring.context;

import spring.factory.AbstractBeanFactory;
import spring.reader.AnnotationBeanDefinitionReader;
import spring.reader.BeanDefinitionReader;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-01-30
 * @since (版本)
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private AbstractBeanFactory abstractBeanFactory;

    public AbstractApplicationContext(AbstractBeanFactory abstractBeanFactory){
        this.abstractBeanFactory = abstractBeanFactory;
    }

    public void refresh(){
        loadBeanDefinitions(abstractBeanFactory);
    }

    protected abstract void loadBeanDefinitions(AbstractBeanFactory abstractBeanFactory);

    @Override
    public Object getBean(String name) {
        return abstractBeanFactory.getBean(name);
    }
}
