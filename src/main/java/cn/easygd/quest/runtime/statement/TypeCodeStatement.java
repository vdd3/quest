package cn.easygd.quest.runtime.statement;

import cn.easygd.quest.runtime.enums.StatementType;

/**
 * @author VD
 */
public class TypeCodeStatement extends StringCodeStatement{

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
        return StatementType.TYPE;
    }
}
