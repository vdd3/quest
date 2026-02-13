package cn.easygd.quest.runtime.statement;

/**
 * @author VD
 */
public class TokenCodeStatement extends CodeStatement {


    public static final String TYPE = "token";

    /**
     * token
     */
    private String token;

    /**
     * token value
     */
    private String value;

    public TokenCodeStatement() {
        this.type = TYPE;
    }

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return "";
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
