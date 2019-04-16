package spring.aop;

import spring.common.annotation.Aspect;
import spring.ioc.bean.BeanDefinition;
import spring.ioc.factory.AspectJAdvisorFactory;
import spring.ioc.factory.DefaultListableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-16
 * @since (版本)
 */
public class BeanFactoryAspectJAdvisorsBuilder {

    private AspectJAdvisorFactory aspectJAdvisorFactory;

    private DefaultListableBeanFactory defaultListableBeanFactory;

    private volatile List<String> aspectBeanNames;

    private Map<String, List<Advisor>> advisorsCache;

    public BeanFactoryAspectJAdvisorsBuilder(AspectJAdvisorFactory aspectJAdvisorFactory, DefaultListableBeanFactory defaultListableBeanFactory) {
        this.aspectJAdvisorFactory = aspectJAdvisorFactory;
        this.defaultListableBeanFactory = defaultListableBeanFactory;
        advisorsCache = new HashMap<>();
    }

    public List<Advisor> buildAspectJAdvisors() {
        Map<String, BeanDefinition> beanDefinitionMap = defaultListableBeanFactory.getBeanDefinitionMap();
        Set<String> beanNameSet = beanDefinitionMap.keySet();
        List<Advisor> advisorList = new ArrayList<>();

        if (aspectBeanNames == null) {
            aspectBeanNames = new ArrayList<>();

            for (String beanName : beanNameSet) {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

                if (beanDefinition.isAspect()) {
                    List<Advisor> classAdvisors = aspectJAdvisorFactory.getAdvisors(beanDefinition);

                    advisorsCache.put(beanName, classAdvisors);
                    advisorList.addAll(classAdvisors);
                    aspectBeanNames.add(beanName);
                }
            }

            return advisorList;
        }

        if (aspectBeanNames.size() == 0) {
            return advisorList;
        }

        for (List<Advisor> item : advisorsCache.values()) {
            advisorList.addAll(item);
        }

        return advisorList;

    }
}
