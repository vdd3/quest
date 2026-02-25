package cn.easygd.quest.engine.runtime.statement;

import cn.easygd.quest.engine.runtime.enums.StatementType;

/**
 * @author VD
 *
 * <p>
 * script statement to code statement , code statement must be in quest moudle
 * </p>
 */
public abstract class CodeStatement {

    /**
     * build content
     *
     * @return content
     */
    public abstract String buildContent();

    /**
     * statement type
     *
     * @return statement type
     */
    public abstract StatementType type();
}
