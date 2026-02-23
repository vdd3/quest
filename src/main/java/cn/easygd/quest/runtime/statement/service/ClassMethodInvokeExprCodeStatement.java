package cn.easygd.quest.runtime.statement.service;

import cn.easygd.quest.runtime.enums.StatementType;
import cn.easygd.quest.runtime.statement.CodeStatement;

/**
 * @author VD
 */
public class ClassMethodInvokeExprCodeStatement extends CodeStatement {

    /**
     * service name
     */
    private String className;

    /**
     * method name
     */
    private String methodName;

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
        return StatementType.CLASS_METHOD_INVOKE_EXPR;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getArguments() {
        return arguments;
    }
}
