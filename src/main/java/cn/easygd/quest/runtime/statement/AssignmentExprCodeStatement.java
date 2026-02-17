package cn.easygd.quest.runtime.statement;

import cn.easygd.quest.runtime.enums.StatementType;

/**
 * @author VD
 */
public class AssignmentExprCodeStatement extends CollectCodeStatement{

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.ARRAY_ACCESS_EXPR;
    }
}
