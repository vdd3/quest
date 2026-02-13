package cn.easygd.quest.runtime.statement;

/**
 * @author VD
 */
public abstract class CodeStatement {

    /**
     * statement type
     */
    protected String type;

    /**
     * build content
     *
     * @return content
     */
    public abstract String buildContent();

    public String getType() {
        return type;
    }

}
