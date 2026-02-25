package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class ForCodeStatement extends CollectCodeStatement {

    /**
     * for control
     */
    private String forControl;



    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.FOR;
    }
}
