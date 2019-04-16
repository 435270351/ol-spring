package spring.ioc.factory;

import spring.aop.AopProxy;
import spring.aop.CglibAopProxy;
import spring.aop.JdkAopProxy;
import spring.aop.TargetSource;

/**
 * 代理工厂
 *
 * @author tangzw
 * @date 2019-04-16
 * @since 1.0.0
 */
public class ProxyFactory extends AdvisedSupport {

    public ProxyFactory() {

    }

    public AopProxy createAopProxy() {
        TargetSource targetSource = getTargetSource();

        if (targetSource.getInterfaces() != null && targetSource.getInterfaces().length != 0) {

            // 通过jdk代理
            return new JdkAopProxy(this);
        } else {
            // 通过cglib代理
            return new CglibAopProxy(this);
        }

    }
}
