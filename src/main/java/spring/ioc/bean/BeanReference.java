package spring.ioc.bean;

import lombok.Data;

/**
 * 对象关系
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
@Data
public class BeanReference {

    /**
     * bean名，根据name依赖注入
     */
    private String beanName;

    /**
     * 类路径，根据type依赖注入
     */
    private String beanClass;

    /**
     * 对象实例
     */
    private Object value;

}
