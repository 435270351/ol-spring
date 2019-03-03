package spring.aop;

import service.En2ServiceImpl;
import spring.factory.AbstractBeanFactory;
import spring.factory.BeanFactory;

/**
 * bean代理创建
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanFactoryAware, BeanPostProcessor {

    private AbstractBeanFactory abstractBeanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        abstractBeanFactory = (AbstractBeanFactory) beanFactory;

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) throws Exception {

        String expression = "execution(* service.EnService.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);

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
        advisedSupport.setMethodMatcher(aspectJExpressionPointcut);

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advisedSupport);
        return cglibAopProxy.getProxy();
    }
}
