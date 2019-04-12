package spring.ioc.factory;

import spring.aop.AdvisedSupport;
import spring.ioc.bean.BeanDefinition;
import spring.ioc.bean.BeanReference;
import spring.ioc.bean.PropertyValue;

import java.lang.reflect.Field;
import java.util.List;

/**
 * AbstractBeanFactory
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory implements BeanDefinitionRegistry {

    private AdvisedSupport advisedSupport = new AdvisedSupport();

    public AdvisedSupport getAdvisedSupport() {
        return advisedSupport;
    }

    @Override
    public void registerBeanDefinition(String key, BeanDefinition val) {
        beanDefinitionMap.put(key, val);
    }

    @Override
    protected Object createBean(BeanDefinition beanDefinition) throws Exception {
        return doCreateBean(beanDefinition);
    }

    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        // 创建bean
        Object object = createBeanInstance(beanDefinition);

        // 属性注入
        return object;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        try {
            String beanClass = beanDefinition.getBeanClass();
            Object object = Class.forName(beanClass).newInstance();

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    protected void populateBean(BeanDefinition beanDefinition, Object object){
        // 获取所有的属性
        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValueList();
        for (PropertyValue propertyValue : propertyValueList) {

            // 如果是BeanReference，则需要依赖注入，否则为常量属性赋值
            if (propertyValue.getValue() instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) propertyValue.getValue();
                Object value = beanReference.getValue();

                if (object == null) {
                    // 获取需要进行依赖注入的对象
                    object = appleBean(beanReference);
                }
                value = getBean()
                beanReference.setValue(object);

                // 通过反射机制赋值
                Field field = bean.getClass().getDeclaredField(propertyValue.getName());
                field.setAccessible(true);
                field.set(bean, object);
            }
        }

        return bean;
    }
}
