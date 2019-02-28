package service;

import spring.aspect.ServiceAspect;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-27
 * @since (版本)
 */
@ServiceAspect
public class En2ServiceImpl implements EnService {

    @Override
    public void ha() {
        System.out.println("完蛋");
    }

}
