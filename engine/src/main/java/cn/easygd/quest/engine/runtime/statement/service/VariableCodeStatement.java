package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

import java.util.Objects;

/**
 * @author VD
 */
public class VariableCodeStatement extends CodeStatement {

    /**
     * variable type
     */
    private String variableType;

    /**
     * variable name
     */
    private String variableName;

    /**
     * variable expression
     */
    private ExpressionCodeStatement expr;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        if (Objects.nonNull(expr)) {
            return String.format("%s %s = %s;", variableType, variableName, expr.buildContent());
        } else {
            return String.format("%s %s;", variableType, variableName);
        }
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.VARIABLE;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public ExpressionCodeStatement getExpr() {
        return expr;
    }

    public void setExpr(ExpressionCodeStatement expr) {
        this.expr = expr;
    }
}
