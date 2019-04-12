package spring.ioc.factory;

import spring.ioc.bean.BeanDefinition;
import spring.ioc.bean.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-11
 * @since (版本)
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    /**
     * name与BeanDefinition的关系
     */
    protected Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


    @Override
    public Object getBean(String name) throws Exception {
        return doGetBean(name);
    }

    protected Object doGetBean(String name)throws Exception {
        // 不区分大小写
        BeanDefinition beanDefinition = beanDefinitionMap.get(name.toLowerCase());

        if (beanDefinition == null) {
            throw new Exception("不存在的bean：" + name);
        }

        Object object = null;

        if (beanDefinition.isSingleton()){
            // 单例模式
            object = beanDefinition.getBean();

            if (object == null) {
                // 创建bean
                object = getSingleton( new ObjectFactory() {
                    @Override
                    public Object getObject() throws Exception {
                        return AbstractBeanFactory.this.createBean(beanDefinition);
                    }
                });

            }

        }else if (beanDefinition.isPrototype()){
            // 原型模式
            object = createBean(beanDefinition);

        }

        return object;
    }

    private Object getSingleton(ObjectFactory objectFactory) throws Exception {
        return objectFactory.getObject();
    }

    protected abstract Object createBean(BeanDefinition beanDefinition)throws Exception;
}
