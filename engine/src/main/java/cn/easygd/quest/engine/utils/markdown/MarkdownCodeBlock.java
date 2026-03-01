package cn.easygd.quest.engine.utils.markdown;

/**
 * @author VD
 */
public class MarkdownCodeBlock extends MarkdownWrapper {

    /**
     * create markdown code block
     *
     * @param language code language
     * @return markdown code block
     */
    public static MarkdownCodeBlock of(String language) {
        return new MarkdownCodeBlock(language);
    }

    /**
     * constructor
     *
     * @param language code language
     */
    private MarkdownCodeBlock(String language) {
        content.append("```").append(language).append(NEW_LINE);
    }

    /**
     * add row
     *
     * @param code code
     * @return this
     */
    public MarkdownCodeBlock addRow(String code) {
        content.append(code).append(NEW_LINE);
        return this;
    }

    /**
     * create markdown
     *
     * @return markdown
     */
    @Override
    public String create() {
        return content.append("```").append(NEW_LINE).toString();
    }
}
