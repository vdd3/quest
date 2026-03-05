package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

/**
 * @author VD
 */
public class WhileCodeStatement extends CodeStatement {

    /**
     * condition
     */
    private ExpressionCodeStatement condition;

    /**
     * block
     */
    private BlockCodeStatement block;


    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("while (%s) %s", condition.buildContent(), block.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.WHILE;
    }

    public ExpressionCodeStatement getCondition() {
        return condition;
    }

    public void setCondition(ExpressionCodeStatement condition) {
        this.condition = condition;
    }

    public BlockCodeStatement getBlock() {
        return block;
    }

    public void setBlock(BlockCodeStatement block) {
        this.block = block;
    }
}
