package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class ArrayExprCodeStatement extends ExpressionCodeStatement {

    /**
     * left expression
     */
    private ExpressionCodeStatement leftExpr;

    /**
     * right expression
     */
    private ExpressionCodeStatement rightExpr;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("%s[%s]", leftExpr.buildContent(), rightExpr.buildContent());
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
}
