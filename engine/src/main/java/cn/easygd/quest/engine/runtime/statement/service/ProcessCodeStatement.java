package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class ProcessCodeStatement extends CollectCodeStatement {

    /**
     * process name
     */
    private String name;

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
