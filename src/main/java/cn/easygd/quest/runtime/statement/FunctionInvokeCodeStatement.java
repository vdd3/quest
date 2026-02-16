package cn.easygd.quest.runtime.statement;

import cn.easygd.quest.runtime.enums.StatementType;

/**
 * @author VD
 */
public class FunctionInvokeCodeStatement extends CodeStatement {

    /**
     * function name
     */
    private String functionName;

    /**
     * arguments
     */
    private String arguments;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return "";
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.FUNCTION_INVOKE_EXPR;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getArguments() {
        return arguments;
    }


}
