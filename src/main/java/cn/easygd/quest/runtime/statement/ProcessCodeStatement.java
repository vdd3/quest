package cn.easygd.quest.runtime.statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VD
 */
public class ProcessCodeStatement extends CodeStatement {

    public static final String TYPE = "processStatement";

    /**
     * process name
     */
    private String name;

    /**
     * statement list
     */
    private List<CodeStatement> statementList = new ArrayList<>();

    public ProcessCodeStatement() {
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
