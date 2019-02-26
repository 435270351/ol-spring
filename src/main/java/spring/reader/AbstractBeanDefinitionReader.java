package spring.reader;

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
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private String location;

    private Map<String,BeanDefinition> register;

    public AbstractBeanDefinitionReader(String location){
        this.location = location.replace('.','/');
        this.register = new HashMap<>();
    }

    public String getLocation() {
        return this.getClass().getClassLoader().getResource("").getPath() + location;
    }

    public Map<String, BeanDefinition> getRegister() {
        return register;
    }
}
