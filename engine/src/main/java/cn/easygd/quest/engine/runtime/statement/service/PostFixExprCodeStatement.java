package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class PostFixExprCodeStatement extends CollectCodeStatement {

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.POSTFIX_EXPR;
    }
}
