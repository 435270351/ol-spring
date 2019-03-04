package spring.aop;

import spring.enums.AdviceEnum;

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

//    private TargetSource targetSource;

    /**
     * 通知对应着匹配关系
     */
    private static Map<AdviceEnum, List<ClassMatcher>> classMatcherMap = new ConcurrentHashMap<>();

    /**
     * 所有的匹配关系
     */
    private static List<ClassMatcher> classMatcherList = new ArrayList<>();

    static {
        List<ClassMatcher> beforeClassMatcherList = new ArrayList<>();
        List<ClassMatcher> aroundClassMatcherList = new ArrayList<>();
        List<ClassMatcher> afterClassMatcherList = new ArrayList<>();

        classMatcherMap.put(AdviceEnum.BEFORE, beforeClassMatcherList);
        classMatcherMap.put(AdviceEnum.AROUND, aroundClassMatcherList);
        classMatcherMap.put(AdviceEnum.AFTER, afterClassMatcherList);

        // todo 测试切入
        String expression = "execution(* com.service.EnService.*(..))";
        AspectJExpressionPointcut beforePointCut = new AspectJExpressionPointcut();
        beforePointCut.setExpression(expression);
        beforeClassMatcherList.add(beforePointCut);

        AspectJExpressionPointcut aroundPointCut = new AspectJExpressionPointcut();
        aroundPointCut.setExpression(expression);
        aroundClassMatcherList.add(aroundPointCut);

        AspectJExpressionPointcut afterPointCut = new AspectJExpressionPointcut();
        afterPointCut.setExpression(expression);
        afterClassMatcherList.add(afterPointCut);

        addClassMatcher(beforePointCut);
        addClassMatcher(aroundPointCut);
        addClassMatcher(afterPointCut);
    }

//    public TargetSource getTargetSource() {
//        return targetSource;
//    }
//
//    public void setTargetSource(TargetSource targetSource) {
//        this.targetSource = targetSource;
//    }

    private List<ClassMatcher> classMatcherList(AdviceEnum val) {
        return classMatcherMap.get(val);
    }

    public List<ClassMatcher> getBeforeClassMatcherList() {
        return classMatcherList(AdviceEnum.BEFORE);
    }

    public List<ClassMatcher> getAroundClassMatcherList() {
        return classMatcherList(AdviceEnum.AROUND);
    }

    public List<ClassMatcher> getAfterClassMatcherList() {
        return classMatcherList(AdviceEnum.AFTER);
    }

    public static void addClassMatcher(ClassMatcher classMatcher) {
        classMatcherList.add(classMatcher);
    }

    public static List<ClassMatcher> getClassMatcherList() {
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
