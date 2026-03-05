package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;
import cn.easygd.quest.engine.runtime.statement.CollectCodeStatement;

import java.util.stream.Collectors;

/**
 * @author VD
 */
public class BlockCodeStatement extends CollectCodeStatement {

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("{\n%s\n}", content.stream().map(CodeStatement::buildContent).collect(Collectors.joining("\n")));
    }

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
