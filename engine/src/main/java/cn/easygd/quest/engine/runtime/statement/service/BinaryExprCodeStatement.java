package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class BinaryExprCodeStatement extends ExpressionCodeStatement {

    /**
     * left expression
     */
    private ExpressionCodeStatement leftExpr;

    /**
     * binary operator
     */
    private String binaryOperator;

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
        return String.format("%s %s %s", leftExpr.buildContent(), binaryOperator, rightExpr.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.BINARY_EXPR;
    }

    public ExpressionCodeStatement getLeftExpr() {
        return leftExpr;
    }

    public void setLeftExpr(ExpressionCodeStatement leftExpr) {
        this.leftExpr = leftExpr;
    }

    public String getBinaryOperator() {
        return binaryOperator;
    }

    public void setBinaryOperator(String binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public ExpressionCodeStatement getRightExpr() {
        return rightExpr;
    }

    public void setRightExpr(ExpressionCodeStatement rightExpr) {
        this.rightExpr = rightExpr;
    }
}
