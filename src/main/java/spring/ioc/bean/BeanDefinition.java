package spring.ioc.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * bean定义关系
 *
 * @author tangzw
 * @date 2019-01-30
 * @since 1.0.0
 */
@Data
public class BeanDefinition {

    /**
     * 对象实例
     */
    private Object bean;

    /**
     * 类路径
     */
    private String beanClass;

    /**
     * 需要注入的属性变量
     */
    private List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 范围
     */
    private String scope;

    public boolean isSingleton() {
        return scope == null || "singleton".equals(scope) || "".equals(scope);
    }

    public boolean isPrototype() {
        return "prototype".equals(scope);
    }
}
