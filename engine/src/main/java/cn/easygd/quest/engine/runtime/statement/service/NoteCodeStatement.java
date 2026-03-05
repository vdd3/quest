package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;

/**
 * @author VD
 */
public class NoteCodeStatement extends CodeStatement {

    /**
     * note
     */
    private String note;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("// TODO %s", note);
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.NOTE;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
