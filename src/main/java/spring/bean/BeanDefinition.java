package spring.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-01-30
 * @since (版本)
 */
@Data
public class BeanDefinition {

    private Object bean;

    private String beanClass;

    private List<PropertyValue> propertyValueList = new ArrayList<>();
}
