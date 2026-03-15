package cn.easygd.quest.engine.runtime.statement.prd;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

/**
 * @author VD
 */
public class BusinessCodeStatement extends CodeStatement {
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
        return StatementType.BUSINESS;
    }
}
