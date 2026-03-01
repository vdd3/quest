package cn.easygd.quest.engine.utils.markdown;

/**
 * @author VD
 */
public class MarkdownTable extends MarkdownWrapper {

    /**
     * separator
     */
    private static final String SEPARATOR = "|";

    /**
     * create table
     *
     * @param headers headers
     * @return table
     */
    public static MarkdownTable of(String... headers) {
        return new MarkdownTable(headers);
    }

    /**
     * constructor
     *
     * @param headers headers
     */
    private MarkdownTable(String... headers) {
        for (String header : headers) {
            content.append(SEPARATOR).append(SPACE).append(header).append(SPACE);
        }
        content.append(SEPARATOR).append(NEW_LINE);
        // align type
        for (String header : headers) {
            content.append(SEPARATOR).append(SPACE).append(":---").append(SPACE);
        }
        content.append(SEPARATOR).append(NEW_LINE);
    }

    /**
     * add row
     *
     * @param cells cells
     * @return this
     */
    public MarkdownTable addRow(String... cells) {
        for (String cell : cells) {
            content.append(SEPARATOR).append(SPACE).append(cell).append(SPACE);
        }
        content.append(SEPARATOR).append(NEW_LINE);
        return this;
    }

    /**
     * create markdown
     *
     * @return markdown
     */
    @Override
    public String create() {
        return content.toString();
    }
}
