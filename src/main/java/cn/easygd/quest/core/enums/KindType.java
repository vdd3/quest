package cn.easygd.quest.core.enums;

/**
 * @author VD
 */
public enum KindType {

    SERVICE("service", "服务"),

    PRD("prd", "产品"),

    ;

    private final String code;

    private final String alis;

    public static KindType getByCode(String code) {
        for (KindType value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    KindType(String code, String alis) {
        this.code = code;
        this.alis = alis;
    }

    public String getCode() {
        return code;
    }

    public String getAlis() {
        return alis;
    }
}
