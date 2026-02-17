package cn.easygd.quest.runtime.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VD
 */
public abstract class CollectCodeStatement extends CodeStatement{

    /**
     * statement list
     */
    protected List<CodeStatement> content = new ArrayList<>();

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return content.stream().map(CodeStatement::buildContent).collect(Collectors.joining());
    }

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
