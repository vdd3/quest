package cn.easygd.quest.runtime.statement;

import cn.easygd.quest.runtime.enums.StatementType;

/**
 * @author VD
 */
public class TokenCodeStatement extends CodeStatement {

    /**
     * token
     */
    private String token;

    /**
     * token value
     */
    private String value;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return "";
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.TOKEN;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }
}
