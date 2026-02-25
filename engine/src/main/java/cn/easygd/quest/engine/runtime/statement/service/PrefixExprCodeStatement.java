package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class PrefixExprCodeStatement extends CollectCodeStatement {
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
        return StatementType.PREFIX_EXPR;
    }
}
