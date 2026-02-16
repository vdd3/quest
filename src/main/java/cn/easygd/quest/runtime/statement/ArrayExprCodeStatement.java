package cn.easygd.quest.runtime.statement;

import cn.easygd.quest.runtime.enums.StatementType;

/**
 * @author VD
 */
public class ArrayExprCodeStatement extends CollectCodeStatement{

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
        return StatementType.ARRAY_ACCESS_EXPR;
    }
}
