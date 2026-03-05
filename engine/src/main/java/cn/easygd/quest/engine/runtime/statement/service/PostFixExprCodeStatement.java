package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class PostFixExprCodeStatement extends ExpressionCodeStatement {

    /**
     * postfix
     */
    private String postfix;

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
        return String.format("%s %s", expr.buildContent(), postfix);
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.POSTFIX_EXPR;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public ExpressionCodeStatement getExpr() {
        return expr;
    }

    public void setExpr(ExpressionCodeStatement expr) {
        this.expr = expr;
    }
}
