package spring.aop;

import spring.enums.AdviceEnum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理相关元素
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AdvisedSupport {

    /**
     * 通知对应着匹配关系
     */
    private static Map<AdviceEnum, List<AopMethod>> classMatcherMap = new ConcurrentHashMap<>();

    /**
     * 所有的匹配关系
     */
    private static List<ClassMatcher> classMatcherList = new ArrayList<>();

    static {
        List<AopMethod> beforeList = new ArrayList<>();
        List<AopMethod> aroundList = new ArrayList<>();
        List<AopMethod> afterList = new ArrayList<>();

        classMatcherMap.put(AdviceEnum.BEFORE, beforeList);
        classMatcherMap.put(AdviceEnum.AROUND, aroundList);
        classMatcherMap.put(AdviceEnum.AFTER, afterList);

    }

    private List<AopMethod> getAopMethod(AdviceEnum val) {
        return classMatcherMap.get(val);
    }

    public List<AopMethod> getBeforeAopMethod() {
        return getAopMethod(AdviceEnum.BEFORE);
    }

    public List<AopMethod> getAroundAopMethod() {
        return getAopMethod(AdviceEnum.AROUND);
    }

    public List<AopMethod> getAfterAopMethod() {
        return getAopMethod(AdviceEnum.AFTER);
    }

    public void addAopMethod(AdviceEnum adviceEnum, AopMethod aopMethod) {
        getAopMethod(adviceEnum).add(aopMethod);
    }

    public void addClassMatcher(ClassMatcher classMatcher) {
        classMatcherList.add(classMatcher);
    }

    public List<ClassMatcher> getClassMatcherList() {
        return classMatcherList;
    }

    public boolean classMatcher(Class clazz) {
        for (ClassMatcher classMatcher : classMatcherList) {
            if (classMatcher.matches(clazz)) {
                return true;
            }
        }

        return false;
    }
}
