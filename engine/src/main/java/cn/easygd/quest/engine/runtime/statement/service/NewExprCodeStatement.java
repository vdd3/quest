package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author VD
 */
public class NewExprCodeStatement extends ExpressionCodeStatement {

    /**
     * class type
     */
    private String classType;

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
            arguments = arguments.replaceAll(",", ", ");
            return String.format("new %s(%s)", classType, arguments);
        } else {
            return String.format("new %s()", classType);
        }
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.NEW_EXPR;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}
