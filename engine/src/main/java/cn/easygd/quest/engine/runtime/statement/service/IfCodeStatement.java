package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

import java.util.Objects;

/**
 * @author VD
 */
public class IfCodeStatement extends CodeStatement {

    /**
     * condition
     */
    private ExpressionCodeStatement condition;

    /**
     * block
     */
    private BlockCodeStatement block;

    /**
     * else block
     */
    private BlockCodeStatement elseBlock;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        if (Objects.nonNull(elseBlock)) {
            return String.format("if (%s) %s else %s", condition.buildContent(), block.buildContent(), elseBlock.buildContent());
        } else {
            return String.format("if (%s) %s", condition.buildContent(), block.buildContent());
        }
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.IF;
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

    public BlockCodeStatement getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(BlockCodeStatement elseBlock) {
        this.elseBlock = elseBlock;
    }
}
