package spring.factory;

import spring.bean.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-26
 * @since (版本)
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null){
            throw new Exception("没有加载bean："+name);
        }

        Object object = beanDefinition.getBean();

        if (object==null){
            object = createBean(beanDefinition);
            object = initializeBean(object,beanDefinition);
            beanDefinition.setBean(object);
        }

        return object;
    }

    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }

    private Object createBean(BeanDefinition beanDefinition){
        return null;
    }

    private Object initializeBean(Object bean,BeanDefinition beanDefinition){
        return null;
    }
}
