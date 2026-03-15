package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

/**
 * @author VD
 */
public class ExprStrCodeStatement extends CodeStatement {

    /**
     * expr
     */
    private ExpressionCodeStatement expr;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("%s;", expr.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.EXPRESSION;
    }

    public ExpressionCodeStatement getExpr() {
        return expr;
    }

    public void setExpr(ExpressionCodeStatement expr) {
        this.expr = expr;
    }
}
