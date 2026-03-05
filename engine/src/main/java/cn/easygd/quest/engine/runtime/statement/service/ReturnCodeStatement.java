package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

import java.util.Objects;

/**
 * @author VD
 */
public class ReturnCodeStatement extends CodeStatement {

    /**
     * return value
     */
    private ExpressionCodeStatement returnValue;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        if (Objects.nonNull(returnValue)) {
            return String.format("return %s;", returnValue.buildContent());
        } else {
            return "return;";
        }
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.RETURN;
    }

    public ExpressionCodeStatement getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ExpressionCodeStatement returnValue) {
        this.returnValue = returnValue;
    }
}
