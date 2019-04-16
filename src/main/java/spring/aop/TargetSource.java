package spring.aop;

/**
 * 被代理的对象
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class TargetSource {

    /**
     * 代理类
     */
    private Class targetClass;

    /**
     * 代理接口
     */
    private Class[] interfaces;

    /**
     * 实例对象
     */
    private Object target;

    public TargetSource(Object target){
        this.target = target;
        this.interfaces = target.getClass().getInterfaces();
        this.targetClass = target.getClass();
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public Class[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class[] interfaces) {
        this.interfaces = interfaces;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
