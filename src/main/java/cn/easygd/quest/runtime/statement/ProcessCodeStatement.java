package cn.easygd.quest.runtime.statement;

import cn.easygd.quest.runtime.enums.StatementType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VD
 */
public class ProcessCodeStatement extends CodeStatement {

    /**
     * process name
     */
    private String name;

    /**
     * statement list
     */
    private List<CodeStatement> statementList = new ArrayList<>();

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
        return StatementType.PROCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
