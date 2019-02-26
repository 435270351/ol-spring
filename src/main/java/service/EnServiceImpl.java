package service;

import spring.aspect.ServiceAspect;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-26
 * @since (版本)
 */
@ServiceAspect
public class EnServiceImpl implements EnService{

    @Override
    public void ha() {
        System.out.println("ha");
    }

}
