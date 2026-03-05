package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class AssignmentExprCodeStatement extends ExpressionCodeStatement {

    /**
     * variable
     */
    private String variable;

    /**
     * assignment operator
     */
    private String assignmentOperator;

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
        return String.format("%s %s %s", variable, assignmentOperator, expr.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.ASSIGNMENT_EXPR;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getAssignmentOperator() {
        return assignmentOperator;
    }

    public void setAssignmentOperator(String assignmentOperator) {
        this.assignmentOperator = assignmentOperator;
    }

    public ExpressionCodeStatement getExpr() {
        return expr;
    }

    public void setExpr(ExpressionCodeStatement expr) {
        this.expr = expr;
    }
}
