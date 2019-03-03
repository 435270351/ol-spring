package service;

import spring.aspect.ResourceAspect;
import spring.aspect.ServiceAspect;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-01-30
 * @since (版本)
 */
@ServiceAspect
public class HelloServiceImpl implements HelloService {

    @ResourceAspect
    public EnService enService;

    public void say() {
        System.out.println("Hellos");
        enService.ha();
    }

    public EnService getEnService() {
        return enService;
    }

    public void setEnService(EnService enService) {
        this.enService = enService;
    }
}