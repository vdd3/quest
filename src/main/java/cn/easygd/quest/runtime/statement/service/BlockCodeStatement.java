package cn.easygd.quest.runtime.statement.service;

import cn.easygd.quest.runtime.enums.StatementType;
import cn.easygd.quest.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class BlockCodeStatement extends CollectCodeStatement {

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.BLOCK;
    }
}
