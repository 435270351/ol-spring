package spring.aop;

import lombok.Data;

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

    private TargetSource targetSource;

    private List<MethodMatcher> methodMatcherList;

    private static Map<String,List<MethodMatcher>> methodMatcherMap = new ConcurrentHashMap<>();

    static {
        List<MethodMatcher> beforeMethodMatcherList = new ArrayList<>();
        List<MethodMatcher> aroundMethodMatcherList = new ArrayList<>();
        List<MethodMatcher> afterMethodMatcherList = new ArrayList<>();

    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public List<MethodMatcher> getMethodMatcherList() {
        return methodMatcherList;
    }

    public void setMethodMatcherList(List<MethodMatcher> methodMatcherList) {
        this.methodMatcherList = methodMatcherList;
    }
}
