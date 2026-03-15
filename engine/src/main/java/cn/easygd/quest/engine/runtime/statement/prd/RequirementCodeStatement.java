package cn.easygd.quest.engine.runtime.statement.prd;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class RequirementCodeStatement extends CollectCodeStatement {
    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.REQUIREMENT;
    }
}
