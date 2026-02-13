package cn.easygd.quest.runtime.statement;

/**
 * @author VD
 */
public class UseCodeStatement extends CodeStatement{

    public static final String TYPE = "useStatement";

    public UseCodeStatement() {
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
}
