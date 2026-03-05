package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class PrefixExprCodeStatement extends ExpressionCodeStatement {

    /**
     * prefix
     */
    private String prefix;

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
        return String.format("%s %s", prefix, expr.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.PREFIX_EXPR;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setExpr(ExpressionCodeStatement expr) {
        this.expr = expr;
    }

    public String getPrefix() {
        return prefix;
    }

    public ExpressionCodeStatement getExpr() {
        return expr;
    }
}
