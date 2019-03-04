package spring.enums;

/**
 * 通知方法
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public enum AdviceEnum {

    BEFORE("前置通知"),

    AROUND("环绕通知"),

    AFTER("后置通知"),;

    private String desc;

    AdviceEnum(String desc) {
        this.desc = desc;
    }
}
