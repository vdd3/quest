package cn.easygd.quest.engine.utils.markdown;

/**
 * @author VD
 */
public class PrdMarkdownInfo extends MarkdownInfo {

    /**
     * constructor
     */
    public PrdMarkdownInfo() {
        h1("系统说明书")
                .h2("简介")
                .highlight("本文档由Quest自动生成，请审核内容以便后续Skill能够更好的实现需求。").newLine()
                .newLine();
    }

    /**
     * create markdown
     *
     * @return markdown
     */
    @Override
    public String create() {
        h2("官方文档")
                .link("语法参考文档", "https://github.com/vdd3/quest/tree/master/engine/src/main/antlr4")
                .link("Quest官方文档", "https://www.yuque.com/vd_developer/quest");
        return content.toString();
    }
}
