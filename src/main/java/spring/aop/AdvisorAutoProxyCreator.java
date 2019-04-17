package spring.aop;

import spring.ioc.factory.AspectJAdvisorFactory;
import spring.ioc.factory.DefaultListableBeanFactory;
import spring.ioc.factory.ProxyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bean代理创建
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AdvisorAutoProxyCreator implements BeanPostProcessor {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * 是否需要代理
     */
    private Map<Object, Boolean> advisedBeans;

    public AdvisorAutoProxyCreator(DefaultListableBeanFactory defaultListableBeanFactory) {
        this.defaultListableBeanFactory = defaultListableBeanFactory;
        advisedBeans = new HashMap<>();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) throws Exception {

        Class clazz = bean.getClass();
        String key = clazz.getName();

        if (advisedBeans.containsKey(key) && !advisedBeans.get(key)) {
            return bean;
        }

        // 获取实际的通知链
        List<Advisor> specificInterceptorList = getAdvicesAndAdvisorsForBean(clazz);

        if (specificInterceptorList.size() == 0) {
            advisedBeans.put(key, false);
            return bean;
        }

        Object proxy = this.createProxy(bean, specificInterceptorList);
        advisedBeans.put(key, true);

        return proxy;
    }

    /**
     * 通过beanClass获取通知链
     *
     * @author:tangzw
     * @date: 2019-04-17
     * @since v1.0.0
     * @param beanClass
     * @return
     */
    protected List<Advisor> getAdvicesAndAdvisorsForBean(Class beanClass) {

        return findEligibleAdvisors(beanClass);
    }

    protected List<Advisor> findEligibleAdvisors(Class beanClass) {
        // 获取所有候选的通知链
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        // 获取符合的通知链
        List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(beanClass, candidateAdvisors);
        // 对通知链进行排序，after-around-before
        Collections.sort(eligibleAdvisors);

        return eligibleAdvisors;
    }

    /**
     * 获取所有候选的通知链
     *
     * @author:tangzw
     * @date: 2019-04-17
     * @since v1.0.0
     * @return
     */
    protected List<Advisor> findCandidateAdvisors() {
        BeanFactoryAspectJAdvisorsBuilder beanFactoryAspectJAdvisorsBuilder = new BeanFactoryAspectJAdvisorsBuilder(new AspectJAdvisorFactory(), defaultListableBeanFactory);
        List<Advisor> advisorList = beanFactoryAspectJAdvisorsBuilder.buildAspectJAdvisors();

        return advisorList;
    }

    /**
     * 获取符合beanClass的通知链
     *
     * @author:tangzw
     * @date: 2019-04-17
     * @since v1.0.0
     * @param beanClass
     * @param candidateAdvisors
     * @return
     */
    protected List<Advisor> findAdvisorsThatCanApply(Class beanClass, List<Advisor> candidateAdvisors) {
        List<Advisor> advisorList = new ArrayList<>();
        for (Advisor advisor : candidateAdvisors) {
            if (advisor.getClassMatcher().matches(beanClass)) {
                advisorList.add(advisor);
            }
        }

        return advisorList;
    }

    /**
     * 生成代理对象
     *
     * @author:tangzw
     * @date: 2019-04-17
     * @since v1.0.0
     * @param bean
     * @param specificInterceptorList
     * @return
     */
    protected Object createProxy(Object bean, List<Advisor> specificInterceptorList) {

        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.setTargetSource(new TargetSource(bean));
        proxyFactory.setAdvisorList(specificInterceptorList);

        return proxyFactory.createAopProxy().getProxy();
    }
}
