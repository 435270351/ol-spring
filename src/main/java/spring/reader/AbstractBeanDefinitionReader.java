package spring.reader;

import spring.bean.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过继承该类来实现自定义的配置读取
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    /**
     * 读取地址路径
     */
    private String location;

    /**
     * beanName与BeanDefinition的关系
     */
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
