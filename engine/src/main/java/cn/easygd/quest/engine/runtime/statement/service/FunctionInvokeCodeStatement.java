package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isNotBlank(arguments)) {
            return String.format("%s(%s)", functionName, arguments);
        } else {
            return String.format("%s()", functionName);
        }
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
