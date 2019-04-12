package spring.ioc.reader;

/**
 * 定义BeanDefinition读取规则
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public interface BeanDefinitionReader {

    /**
     * 加载bean关系
     *
     * @author:tangzw
     * @date: 2019-02-27
     * @since v1.0.0
     */
    void loadBeanDefinitions(String[] locations);
}
