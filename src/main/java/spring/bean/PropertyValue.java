package spring.bean;

import lombok.Data;

/**
 * 属性关系
 *
 * @author tangzw
 * @date 2019-01-30
 * @since 1.0.0
 */
@Data
public class PropertyValue {

    /**
     * 属性名
     */
    private String name;

    /**
     * 属性值
     */
    private Object value;

    public PropertyValue(String name,Object value){
        this.name = name;
        this.value = value;
    }
}
