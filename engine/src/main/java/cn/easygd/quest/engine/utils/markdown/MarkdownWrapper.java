package cn.easygd.quest.engine.utils.markdown;

/**
 * @author VD
 */
public abstract class MarkdownWrapper {

    /**
     * new line
     */
    protected static final String NEW_LINE = "\n";

    /**
     * space
     */
    protected static final String SPACE = " ";

    /**
     * content
     */
    protected StringBuilder content = new StringBuilder();

    /**
     * create markdown
     *
     * @return markdown
     */
    public abstract String create();
}
