package spring.bean;

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
     * 属性变量
     */
    private List<PropertyValue> propertyValueList = new ArrayList<>();
}
