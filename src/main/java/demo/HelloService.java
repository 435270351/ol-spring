package demo;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-20
 * @since (版本)
 */
public class HelloService implements Hello {

    public String say() {
        System.out.println("测试咯");

        return "hh";
    }

    @Override
    public String hello() {
        System.out.println("hello");
        return "lala";
    }

}
