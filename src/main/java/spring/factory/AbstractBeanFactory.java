package spring.factory;

import org.apache.commons.lang.StringUtils;
import spring.bean.BeanDefinition;
import spring.bean.BeanReference;
import spring.bean.PropertyValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AbstractBeanFactory
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    /**
     * name与BeanDefinition的关系
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public Object getBean(String name) throws Exception {
        // 不区分大小写
        BeanDefinition beanDefinition = beanDefinitionMap.get(name.toLowerCase());
        if (beanDefinition == null) {
            throw new Exception("没有加载bean：" + name);
        }

        Object object = beanDefinition.getBean();

        if (object == null) {
            // 创建bean
            object = createBean(beanDefinition);
            if (object == null){
                // 创建失败
                return null;
            }

            // 属性注入
            object = initializeBean(object, beanDefinition);
            beanDefinition.setBean(object);
        }

        return object;
    }

    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }

    protected Object createBean(BeanDefinition beanDefinition) {
        try {
            String beanClass = beanDefinition.getBeanClass();
            Object object = Class.forName(beanClass).newInstance();

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Object initializeBean(Object bean, BeanDefinition beanDefinition) throws Exception {
        // 获取所有的属性
        List<PropertyValue> list = beanDefinition.getPropertyValueList();
        for (PropertyValue propertyValue : list) {
            // 如果是BeanReference，则需要依赖注入，否则为常量属性赋值
            if (propertyValue.getValue() instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) propertyValue.getValue();
                Object object = beanReference.getValue();

                if (object == null) {
                    // 获取需要进行依赖注入的对象
                    object = appleBean(beanReference);
                }
                beanReference.setValue(object);

                // 通过反射机制赋值
                Field field = bean.getClass().getDeclaredField(propertyValue.getName());
                field.setAccessible(true);
                field.set(bean, object);
            }
        }

        return bean;
    }

    /**
     * 获取需要进行依赖注入的对象
     *
     * @author:tangzw
     * @date: 2019-02-27
     * @since v1.0.0
     * @param beanReference
     * @return
     * @throws Exception
     */
    private Object appleBean(BeanReference beanReference) throws Exception {
        Object object = null;

        if (StringUtils.isNotEmpty(beanReference.getBeanName())) {
            // 根据name获取对象
            object = appleName(beanReference);

        } else {
            // 根据type获取对象
            object = appleType(beanReference);

        }

        return object;
    }

    /**
     * 根据name获取对象
     *
     * @author:tangzw
     * @date: 2019-02-27
     * @since v1.0.0
     * @param beanReference
     * @return
     * @throws Exception
     */
    private Object appleName(BeanReference beanReference) throws Exception {
        return getBean(beanReference.getBeanName());
    }

    /**
     * 根据type获取对象
     *
     * @author:tangzw
     * @date: 2019-02-27
     * @since v1.0.0
     * @param beanReference
     * @return
     * @throws Exception
     */
    private Object appleType(BeanReference beanReference) throws Exception {
        Set<String> nameSet = beanDefinitionMap.keySet();

        Set<String> beanNameSet = new HashSet<>();

        Object object = null;
        for (String name : nameSet) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(name);
            Class cla = Class.forName(beanDefinition.getBeanClass());
            for (Class in : cla.getInterfaces()) {
                if (in.getName().equals(beanReference.getBeanClass())) {
                    object = getBean(name);
                    beanNameSet.add(name);
                }
            }
        }

        if (beanNameSet.size() > 1) {
            throw new Exception("存在多个符合的bean：" + beanNameSet);
        }

        return object;
    }

}
