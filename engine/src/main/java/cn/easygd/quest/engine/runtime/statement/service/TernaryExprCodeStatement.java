package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class TernaryExprCodeStatement extends ExpressionCodeStatement {

    /**
     * condition
     */
    private ExpressionCodeStatement condition;

    /**
     * true expression
     */
    private ExpressionCodeStatement trueExpr;

    /**
     * false expression
     */
    private ExpressionCodeStatement falseExpr;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("%s ? %s : %s", condition.buildContent(), trueExpr.buildContent(), falseExpr.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.TERNARY_EXPR;
    }

    public ExpressionCodeStatement getCondition() {
        return condition;
    }

    public void setCondition(ExpressionCodeStatement condition) {
        this.condition = condition;
    }

    public ExpressionCodeStatement getTrueExpr() {
        return trueExpr;
    }

    public void setTrueExpr(ExpressionCodeStatement trueExpr) {
        this.trueExpr = trueExpr;
    }

    public ExpressionCodeStatement getFalseExpr() {
        return falseExpr;
    }

    public void setFalseExpr(ExpressionCodeStatement falseExpr) {
        this.falseExpr = falseExpr;
    }
}
