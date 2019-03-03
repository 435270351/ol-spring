package spring.aop;

import lombok.Data;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-03-03
 * @since (版本)
 */
@Data
public abstract class AbstractAopProxy implements AopProxy{

    private AdvisedSupport advisedSupport;

    protected AbstractAopProxy(AdvisedSupport advisedSupport){
        this.advisedSupport = advisedSupport;
    }

    public AdvisedSupport getAdvisedSupport() {
        return advisedSupport;
    }

    public void setAdvisedSupport(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }
}
