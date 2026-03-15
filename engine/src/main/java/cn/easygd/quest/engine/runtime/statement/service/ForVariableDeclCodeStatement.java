package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 */
public class ForVariableDeclCodeStatement extends ForControlCodeStatement {

    /**
     * variable name
     */
    private String variableName;

    /**
     * variable type
     */
    private String variableType;

    /**
     * variable value
     */
    private String variableValue;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("%s %s : %s", variableType, variableName, variableValue);
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

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }
}
