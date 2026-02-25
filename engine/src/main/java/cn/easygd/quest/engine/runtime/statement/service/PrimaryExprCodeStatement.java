package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.StringCodeStatement;

/**
 * @author VD
 */
public class PrimaryExprCodeStatement extends StringCodeStatement {

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return getContent();
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.PRIMARY_EXPR;
    }

}
