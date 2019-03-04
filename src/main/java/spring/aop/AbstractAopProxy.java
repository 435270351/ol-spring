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

    private TargetSource targetSource;

    protected AbstractAopProxy(AdvisedSupport advisedSupport,TargetSource targetSource){
        this.advisedSupport = advisedSupport;
        this.targetSource = targetSource;
    }

    public AdvisedSupport getAdvisedSupport() {
        return advisedSupport;
    }

    public void setAdvisedSupport(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }
}
