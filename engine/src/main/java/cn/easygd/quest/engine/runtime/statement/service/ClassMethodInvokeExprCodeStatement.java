package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isNotBlank(arguments)) {
            return String.format("%s.%s(%s)", className, methodName, arguments);
        } else {
            return String.format("%s.%s()", className, methodName);
        }
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
