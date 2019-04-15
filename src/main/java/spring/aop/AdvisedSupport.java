package spring.aop;

import spring.common.enums.AdviceEnum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
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
     * 通知集合
     */
    private static List<Advisor> advisorList = new ArrayList<>();

    /**
     * 判断是否需要进行代理
     *
     * @author:tangzw
     * @date: 2019-04-15
     * @since v1.0.0
     * @param clazz
     * @return
     */
    public boolean classMatcher(Class clazz) {
        for (Advisor advisor : advisorList) {
            ClassMatcher classMatcher = advisor.getClassMatcher();
            if (classMatcher.matches(clazz)) {
                return true;
            }
        }

        return false;
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
    public List<Advisor> getChainList(Method method){
        List<Advisor> chainList = new ArrayList<>();

        for (Advisor advisor : advisorList) {
            ClassMatcher classMatcher = advisor.getClassMatcher();
            if (classMatcher.matches(method)) {
                chainList.add(advisor);
            }
        }

        return chainList;
    }

    public void addAdvisor(Advisor advisor){
        advisorList.add(advisor);
    }

    /**
     * 改变
     */
    public void changeAdvisorSort(){
        advisorList.sort(new Comparator<Advisor>() {
            @Override
            public int compare(Advisor o1, Advisor o2) {
                AdviceEnum a1 = AdviceEnum.parse(o1.getMethodName());
                AdviceEnum a2 = AdviceEnum.parse(o2.getMethodName());

                return a1.getVal() - a2.getVal();
            }
        });
    }

}
