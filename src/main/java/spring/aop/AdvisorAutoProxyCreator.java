package spring.aop;

import spring.ioc.factory.DefaultListableBeanFactory;

/**
 * bean代理创建
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AdvisorAutoProxyCreator implements BeanPostProcessor {

    private AdvisedSupport advisedSupport;

    public AdvisorAutoProxyCreator(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) throws Exception {

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
