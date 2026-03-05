package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class CastExprCodeStatement extends ExpressionCodeStatement {

    /**
     * cast type
     */
    private String castType;

    /**
     * expression
     */
    private ExpressionCodeStatement expr;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("(%s)%s", castType, expr.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.CAST_EXPR;
    }

    public String getCastType() {
        return castType;
    }

    public void setCastType(String castType) {
        this.castType = castType;
    }

    public ExpressionCodeStatement getExpr() {
        return expr;
    }

    public void setExpr(ExpressionCodeStatement expr) {
        this.expr = expr;
    }
}
