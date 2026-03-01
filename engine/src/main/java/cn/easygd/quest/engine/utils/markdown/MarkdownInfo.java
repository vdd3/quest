package cn.easygd.quest.engine.utils.markdown;

/**
 * @author VD
 */
public abstract class MarkdownInfo extends MarkdownWrapper {

    /**
     * new line
     *
     * @return MarkdownInfo
     */
    public MarkdownInfo newLine() {
        content.append(NEW_LINE);
        return this;
    }

    /**
     * Add level 1 heading
     *
     * @param text heading content
     * @return current builder instance
     */
    public MarkdownInfo h1(String text) {
        content.append("#").append(SPACE).append(text).append(NEW_LINE).append(NEW_LINE);
        return this;
    }

    /**
     * Add level 2 heading
     *
     * @param text heading content
     * @return current builder instance
     */
    public MarkdownInfo h2(String text) {
        content.append("##").append(SPACE).append(text).append(NEW_LINE).append(NEW_LINE);
        return this;
    }

    /**
     * Add level 3 heading
     *
     * @param text heading content
     * @return current builder instance
     */
    public MarkdownInfo h3(String text) {
        content.append("###").append(SPACE).append(text).append(NEW_LINE).append(NEW_LINE);
        return this;
    }

    /**
     * Add level 4 heading
     *
     * @param text heading content
     * @return current builder instance
     */
    public MarkdownInfo h4(String text) {
        content.append("####").append(SPACE).append(text).append(NEW_LINE).append(NEW_LINE);
        return this;
    }

    /**
     * Add level 5 heading
     *
     * @param text heading content
     * @return current builder instance
     */
    public MarkdownInfo h5(String text) {
        content.append("#####").append(SPACE).append(text).append(NEW_LINE).append(NEW_LINE);
        return this;
    }

    /**
     * Add level 6 heading
     *
     * @param text heading content
     * @return current builder instance
     */
    public MarkdownInfo h6(String text) {
        content.append("######").append(SPACE).append(text).append(NEW_LINE).append(NEW_LINE);
        return this;
    }

    /**
     * Add block quote
     *
     * @param text quote content
     * @return current builder instance
     */
    public MarkdownInfo blockquote(String text) {
        content.append(">").append(SPACE).append(text).append(NEW_LINE).append(NEW_LINE);
        return this;
    }

    /**
     * Add unordered list item
     *
     * @param text list item content
     * @return current builder instance
     */
    public MarkdownInfo unorderedListItem(String text) {
        content.append("-").append(SPACE).append(text).append(NEW_LINE);
        return this;
    }

    /**
     * Add plain text
     *
     * @param text text content
     * @return current builder instance
     */
    public MarkdownInfo text(String text) {
        content.append(text);
        return this;
    }

    /**
     * Add bold text
     *
     * @param text text content
     * @return current builder instance
     */
    public MarkdownInfo bold(String text) {
        content.append("**").append(text).append("**");
        return this;
    }

    /**
     * Add italic text
     *
     * @param text text content
     * @return current builder instance
     */
    public MarkdownInfo italic(String text) {
        content.append("*").append(text).append("*");
        return this;
    }

    /**
     * Add bold and italic text
     *
     * @param text text content
     * @return current builder instance
     */
    public MarkdownInfo boldItalic(String text) {
        content.append("***").append(text).append("***");
        return this;
    }

    /**
     * Add strikethrough text
     *
     * @param text text content
     * @return current builder instance
     */
    public MarkdownInfo strikethrough(String text) {
        content.append("~~").append(text).append("~~");
        return this;
    }

    /**
     * Add highlighted text
     *
     * @param text text content
     * @return current builder instance
     */
    public MarkdownInfo highlight(String text) {
        content.append("<mark>").append(text).append("</mark>");
        return this;
    }

    /**
     * add custom markdown type
     *
     * @param markdownWrapper markdownWrapper
     * @return MarkdownInfo
     */
    public MarkdownInfo append(MarkdownWrapper markdownWrapper) {
        content.append(markdownWrapper.create());
        return this;
    }

    /**
     * Add link
     *
     * @param text link display text
     * @param url  link address
     * @return current builder instance
     */
    public MarkdownInfo link(String text, String url) {
        content.append("[").append(text).append("](").append(url).append(")").append(NEW_LINE);
        return this;
    }

    /**
     * Add link with title
     *
     * @param text  link display text
     * @param url   link address
     * @param title link title
     * @return current builder instance
     */
    public MarkdownInfo linkWithTitle(String text, String url, String title) {
        content.append("[").append(text).append("](").append(url).append(" \"").append(title).append("\")").append(NEW_LINE);
        return this;
    }

    /**
     * Add reference link
     *
     * @param text      reference text
     * @param reference reference identifier
     * @return current builder instance
     */
    public MarkdownInfo referenceLink(String text, String reference) {
        content.append("[").append(text).append("][").append(reference).append("]").append(NEW_LINE);
        return this;
    }

    /**
     * Define reference link
     *
     * @param reference reference identifier
     * @param url       link address
     * @return current builder instance
     */
    public MarkdownInfo defineReference(String reference, String url) {
        content.append("[").append(reference).append("]: ").append(url).append(NEW_LINE);
        return this;
    }
}
