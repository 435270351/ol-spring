package spring.aop;

import spring.factory.AbstractBeanFactory;
import spring.factory.BeanFactory;

/**
 * bean代理创建
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor {

//    private AbstractBeanFactory abstractBeanFactory;
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) {
//        abstractBeanFactory = (AbstractBeanFactory) beanFactory;
//
//    }

    @Override
    public Object postProcessBeforeInitialization(Object bean) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) throws Exception {

        String expression = "execution(* service.EnService.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);

        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression(expression);


        // 切面匹配不成功
        if (!aspectJExpressionPointcut.matches(bean.getClass())) {
            return bean;
        }

        TargetSource targetSource = new TargetSource();
        targetSource.setTargetClass(bean.getClass());
        targetSource.setInterfaces(bean.getClass().getInterfaces());
        targetSource.setTarget(bean);

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.getMethodMatcherList().add(aspectJExpressionPointcut);
        advisedSupport.getMethodMatcherList().add(pointcut2);

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advisedSupport);
        return cglibAopProxy.getProxy();
    }
}
