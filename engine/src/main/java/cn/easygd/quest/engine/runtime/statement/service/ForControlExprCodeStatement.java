package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class ForControlExprCodeStatement extends ForControlCodeStatement {

    /**
     * left expr
     */
    private ExpressionCodeStatement leftExpr;

    /**
     * right expr
     */
    private ExpressionCodeStatement rightExpr;

    /**
     * condition expr
     */
    private ExpressionCodeStatement conditionExpr;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("%s ; %s ; %s", leftExpr.buildContent(), conditionExpr.buildContent(), rightExpr.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.FOR_CONTROL;
    }

    public ExpressionCodeStatement getLeftExpr() {
        return leftExpr;
    }

    public void setLeftExpr(ExpressionCodeStatement leftExpr) {
        this.leftExpr = leftExpr;
    }

    public ExpressionCodeStatement getRightExpr() {
        return rightExpr;
    }

    public void setRightExpr(ExpressionCodeStatement rightExpr) {
        this.rightExpr = rightExpr;
    }

    public ExpressionCodeStatement getConditionExpr() {
        return conditionExpr;
    }

    public void setConditionExpr(ExpressionCodeStatement conditionExpr) {
        this.conditionExpr = conditionExpr;
    }
}
