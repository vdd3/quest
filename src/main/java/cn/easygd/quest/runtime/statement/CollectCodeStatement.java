package cn.easygd.quest.runtime.statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VD
 */
public abstract class CollectCodeStatement extends CodeStatement{

    /**
     * statement list
     */
    protected List<CodeStatement> content = new ArrayList<>();

    public void add(CodeStatement content) {
        this.content.add(content);
    }

    public void addAll(List<CodeStatement> content) {
        this.content.addAll(content);
    }

    public List<CodeStatement> getContent() {
        return content;
    }

}
