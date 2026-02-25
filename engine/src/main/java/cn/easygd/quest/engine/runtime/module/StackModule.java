package cn.easygd.quest.engine.runtime.module;

import cn.easygd.quest.engine.runtime.statement.CodeStatement;

/**
 * @author VD
 */
public class StackModule implements QuestModule{

    /**
     * code statement
     */
    private CodeStatement codeStatement;

    public void setCodeStatement(CodeStatement codeStatement) {
        this.codeStatement = codeStatement;
    }

    public CodeStatement getCodeStatement() {
        return codeStatement;
    }
}
