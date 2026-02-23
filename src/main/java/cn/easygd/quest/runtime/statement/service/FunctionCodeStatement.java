package cn.easygd.quest.runtime.statement.service;

import cn.easygd.quest.runtime.enums.StatementType;
import cn.easygd.quest.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class FunctionCodeStatement extends CollectCodeStatement {
    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.FUNCTION;
    }
}
