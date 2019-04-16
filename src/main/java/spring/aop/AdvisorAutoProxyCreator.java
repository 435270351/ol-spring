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

        if (advisedBeans.containsKey(clazz.getName())){
            return bean;
        }

        List<Advisor> specificInterceptorList = getAdvicesAndAdvisorsForBean(clazz);

        if (specificInterceptorList.size() == 0){
            advisedBeans.put(clazz.getName(),false);
            return bean;
        }

        Object proxy = this.createProxy(bean,specificInterceptorList);
        advisedBeans.put(clazz.getName(),true);

        return proxy;
    }

    protected List<Advisor> getAdvicesAndAdvisorsForBean(Class beanClass){

        return findEligibleAdvisors(beanClass);
    }

    protected List<Advisor> findEligibleAdvisors(Class beanClass){
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(beanClass,candidateAdvisors);
        Collections.sort(eligibleAdvisors);

        return eligibleAdvisors;
    }

    protected List<Advisor> findCandidateAdvisors(){
        BeanFactoryAspectJAdvisorsBuilder beanFactoryAspectJAdvisorsBuilder = new BeanFactoryAspectJAdvisorsBuilder(new AspectJAdvisorFactory(),defaultListableBeanFactory);
        List<Advisor> advisorList = beanFactoryAspectJAdvisorsBuilder.buildAspectJAdvisors();

        return advisorList;
    }

    protected List<Advisor> findAdvisorsThatCanApply(Class beanClass, List<Advisor> candidateAdvisors){
        List<Advisor> advisorList = new ArrayList<>();
        for (Advisor advisor:candidateAdvisors){
            if (advisor.getClassMatcher().matches(beanClass)){
                advisorList.add(advisor);
            }
        }

        return advisorList;
    }

    protected Object createProxy(Object bean,List<Advisor> specificInterceptorList){

        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.setTargetSource(new TargetSource(bean));
        proxyFactory.setAdvisorList(specificInterceptorList);

        return proxyFactory.createAopProxy().getProxy();
    }
}
