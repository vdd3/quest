package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

/**
 * @author VD
 */
public class ForCodeStatement extends CodeStatement {

    /**
     * for control
     */
    private ForControlCodeStatement forControl;

    /**
     * for block
     */
    private BlockCodeStatement forBlock;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("for (%s) %s", forControl.buildContent(), forBlock.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.FOR;
    }

    public ForControlCodeStatement getForControl() {
        return forControl;
    }

    public void setForControl(ForControlCodeStatement forControl) {
        this.forControl = forControl;
    }

    public BlockCodeStatement getForBlock() {
        return forBlock;
    }

    public void setForBlock(BlockCodeStatement forBlock) {
        this.forBlock = forBlock;
    }
}
