package spring.ioc.factory;

import spring.aop.Advisor;
import spring.aop.ClassMatcher;
import spring.aop.TargetSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 代理相关元素
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AdvisedSupport {

    /**
     * 目标类
     */
    private TargetSource targetSource;

    /**
     * 通知集合
     */
    private List<Advisor> advisorList;

    /**
     * 通知链生产工厂
     */
    private AdvisorChainFactory advisorChainFactory;

    /**
     * 对方法做缓存
     */
    private HashMap<String,List> methodCache;

    public AdvisedSupport(){
        advisorChainFactory = new AdvisorChainFactory();
        methodCache = new HashMap<>();
    }

    /**
     * 获取通知连
     *
     * @author:tangzw
     * @date: 2019-04-15
     * @since v1.0.0
     * @param method
     * @return
     */
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method){
        String key = method.getName();

        if (methodCache.containsKey(key)){
            return methodCache.get(key);
        }

        List<Object> chainList = advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this,method);
        methodCache.put(key,chainList);

        return chainList;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public void setAdvisorList(List<Advisor> advisorList) {
        this.advisorList = advisorList;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public List<Advisor> getAdvisorList() {
        return advisorList;
    }
}
