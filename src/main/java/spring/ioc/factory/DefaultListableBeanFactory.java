package spring.ioc.factory;

import org.apache.commons.lang.StringUtils;
import spring.aop.AdvisorAutoProxyCreator;
import spring.ioc.bean.BeanDefinition;
import spring.ioc.bean.BeanReference;
import spring.ioc.bean.PropertyValue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (beanDefinitionMap.containsKey(key)) {
            throw new RuntimeException("Bean name " + key + " is already used");
        }
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
        populateBean(beanDefinition, object);

        // 生成代理对象
        object = initializeBean(beanDefinition, object);

        return object;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        try {
            Object object = beanDefinition.getBeanClass().newInstance();

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    protected void populateBean(BeanDefinition beanDefinition, Object object) throws Exception {
        // 获取所有的属性
        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValueList();
        for (PropertyValue propertyValue : propertyValueList) {

            // 如果是BeanReference，则需要依赖注入，否则为常量属性赋值
            if (propertyValue.getValue() instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) propertyValue.getValue();

                if (StringUtils.isNotEmpty(beanReference.getBeanName())) {
                    // 根据name获取对象
                    this.autowireByName(beanReference);

                } else {
                    // 根据type获取对象
                    this.autowireByType(beanReference);
                }

                // 通过反射机制赋值
                Field field = object.getClass().getDeclaredField(propertyValue.getName());
                field.setAccessible(true);
                field.set(object, beanReference.getValue());
            }
        }

    }

    protected void autowireByName(BeanReference beanReference) throws Exception {
        Object object = getBean(beanReference.getBeanName());
        beanReference.setValue(object);
    }

    protected void autowireByType(BeanReference beanReference) throws Exception {
        Set<String> nameSet = beanDefinitionMap.keySet();

        Set<String> beanNameSet = new HashSet<>();

        Object object = null;
        for (String name : nameSet) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(name);
            Class cla = beanDefinition.getBeanClass();
            for (Class in : cla.getInterfaces()) {
                if (in.getName().equals(beanReference.getBeanClass())) {
                    object = getBean(name);
                    beanNameSet.add(name);
                }
            }
        }

        if (beanNameSet.size() > 1) {
            throw new RuntimeException("存在多个符合的bean：" + beanNameSet);
        }

        beanReference.setValue(object);
    }

    protected Object initializeBean(BeanDefinition beanDefinition, Object object) throws Exception {
        AdvisorAutoProxyCreator proxyCreator = new AdvisorAutoProxyCreator(this);
        // 前置操作
        Object proxyBean = proxyCreator.postProcessBeforeInitialization(object);

        // 后置操作
        proxyBean = proxyCreator.postProcessAfterInitialization(object);

        beanDefinition.setBean(proxyBean);

        return proxyBean;
    }
}
