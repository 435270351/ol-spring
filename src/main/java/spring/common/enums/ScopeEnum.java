package spring.common.enums;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-11
 * @since (版本)
 */
public enum ScopeEnum {

    /**
     * 单例
     */
    singleton,

    /**
     * 原型
     */
    prototype;

    public static ScopeEnum parse(String val) {
        if (val == null) {
            return null;
        }

        for (ScopeEnum item : ScopeEnum.values()) {
            if (item.name().equals(val)) {
                return item;
            }
        }

        return null;
    }
}
