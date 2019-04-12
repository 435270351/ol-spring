package spring.aop;

import spring.ioc.factory.DefaultListableBeanFactory;

/**
 * bean代理创建
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    public AspectJAwareAdvisorAutoProxyCreator(DefaultListableBeanFactory defaultListableBeanFactory) {
        this.defaultListableBeanFactory = defaultListableBeanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) throws Exception {

        AdvisedSupport advisedSupport = defaultListableBeanFactory.getAdvisedSupport();

        // 切面匹配不成功
        if (!advisedSupport.classMatcher(bean.getClass())) {
            return bean;
        }

        TargetSource targetSource = new TargetSource();
        targetSource.setTargetClass(bean.getClass());
        targetSource.setInterfaces(bean.getClass().getInterfaces());
        targetSource.setTarget(bean);

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advisedSupport, targetSource);
        return cglibAopProxy.getProxy();
    }
}
