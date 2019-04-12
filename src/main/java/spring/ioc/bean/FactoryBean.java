package spring.ioc.bean;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-11
 * @since (版本)
 */
public interface FactoryBean {

    Object getObject() throws Exception;
}
