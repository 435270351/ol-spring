package spring.aop.invocation;

import spring.aop.Advisor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-15
 * @since (版本)
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    private List<MethodInterceptor> chainList;

    private Method method;

    private Object target;

    private Object[] arguments;

    private int currentIndex = 0;

    public ReflectiveMethodInvocation(Object target,Method method,Object[] arguments,List<MethodInterceptor> chainList){
       this.target = target;
       this.method = method;
       this.arguments = arguments;
        this.chainList = chainList;
    }

    @Override
    public Object proceed() throws Exception {
        if (currentIndex == chainList.size()){
            return invokeJoinpoint();
        }

        MethodInterceptor methodInterceptor = chainList.get(currentIndex++);
        Object object = methodInterceptor.invoke(this);

        return object;
    }

    public Object invokeJoinpoint() throws Exception {
        return method.invoke(target,arguments);
    }
}