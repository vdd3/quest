package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class PrimaryExprCodeStatement extends ExpressionCodeStatement {

    /**
     * primary
     */
    private String primary;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return primary;
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

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
