package cn.easygd.quest.engine.runtime.statement;

/**
 * @author VD
 */
public abstract class StringCodeStatement extends CodeStatement {

    /**
     * content
     */
    private String content;

    public void add(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
