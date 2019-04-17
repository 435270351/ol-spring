package spring.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 通知方法
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public enum AdviceEnum {

    BEFORE("前置通知", 10),

    AROUND("环绕通知", 5),

    AFTER("后置通知", 1),;

    private String desc;

    /**
     * 用于对通知方法进行排序
     */
    private int val;

    AdviceEnum(String desc, int val) {
        this.desc = desc;
        this.val = val;
    }

    public static AdviceEnum parse(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        for (AdviceEnum item : AdviceEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }

        return null;
    }

    public String getDesc() {
        return desc;
    }

    public int getVal() {
        return val;
    }
}
