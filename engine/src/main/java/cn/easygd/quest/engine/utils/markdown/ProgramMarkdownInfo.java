package cn.easygd.quest.engine.utils.markdown;

/**
 * @author VD
 */
public class ProgramMarkdownInfo extends MarkdownInfo {

    /**
     * timing diagram
     */
    private static final MarkdownCodeBlock DIAGRAM = MarkdownCodeBlock.of("mermaid")
            .addRow("sequenceDiagram")
            .addRow("autonumber")
            .addRow("participant U as 用户")
            .addRow("participant M as 系统说明书")
            .addRow("participant A as Agent")
            .addRow("participant P as 系统")
            .addRow("U->>M: 审核系统说明书")
            .addRow("alt 说明书符合需求")
            .addRow("A->>M: 阅读系统说明书")
            .addRow("M->>A: 查看实体模块")
            .addRow("A->>P: 按照要求生成或者修改实体模块")
            .addRow("M->>A: 查看逻辑模块")
            .addRow("A->>P: 按照要求添加或修改逻辑模块")
            .addRow("else 说明书不符合需求")
            .addRow("M->>U: 用户继续修改系统说明书")
            .addRow("end")
            .addRow("Note over A,P: 结束");

    /**
     * constructor
     */
    public ProgramMarkdownInfo() {
        h1("系统说明书")
                .h2("简介")
                .highlight("本文档由Quest自动生成，请审核实体模块和逻辑模块以便后续Skill能够更好的实现需求。").newLine()
                .newLine()
                .h2("执行顺序")
                .append(DIAGRAM).newLine();
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
