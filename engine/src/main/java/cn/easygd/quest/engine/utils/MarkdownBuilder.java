package cn.easygd.quest.engine.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Markdown语法构建器工具类
 * 提供完整的Markdown语法构建功能
 *
 * @author Quest Team
 * @since 1.0.0
 */
public class MarkdownBuilder {

    private final StringBuilder content;
    private final List<String> tableHeaders;
    private final List<List<String>> tableRows;

    /**
     * 构造函数
     */
    public MarkdownBuilder() {
        this.content = new StringBuilder();
        this.tableHeaders = new ArrayList<>();
        this.tableRows = new ArrayList<>();
    }

    // ==================== 基础文本格式 ====================

    /**
     * 添加普通文本
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder text(String text) {
        content.append(text);
        return this;
    }

    /**
     * 添加粗体文本
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder bold(String text) {
        content.append("**").append(text).append("**");
        return this;
    }

    /**
     * 添加斜体文本
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder italic(String text) {
        content.append("*").append(text).append("*");
        return this;
    }

    /**
     * 添加粗体斜体文本
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder boldItalic(String text) {
        content.append("***").append(text).append("***");
        return this;
    }

    /**
     * 添加删除线文本
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder strikethrough(String text) {
        content.append("~~").append(text).append("~~");
        return this;
    }

    /**
     * 添加下划线文本（HTML方式）
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder underline(String text) {
        content.append("<u>").append(text).append("</u>");
        return this;
    }

    /**
     * 添加高亮文本（HTML方式）
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder highlight(String text) {
        content.append("<mark>").append(text).append("</mark>");
        return this;
    }

    /**
     * 添加上标文本
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder superscript(String text) {
        content.append("<sup>").append(text).append("</sup>");
        return this;
    }

    /**
     * 添加下标文本
     *
     * @param text 文本内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder subscript(String text) {
        content.append("<sub>").append(text).append("</sub>");
        return this;
    }

    // ==================== 标题 ====================

    /**
     * 添加一级标题
     *
     * @param text 标题内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder h1(String text) {
        content.append("# ").append(text).append("\n\n");
        return this;
    }

    /**
     * 添加二级标题
     *
     * @param text 标题内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder h2(String text) {
        content.append("## ").append(text).append("\n\n");
        return this;
    }

    /**
     * 添加三级标题
     *
     * @param text 标题内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder h3(String text) {
        content.append("### ").append(text).append("\n\n");
        return this;
    }

    /**
     * 添加四级标题
     *
     * @param text 标题内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder h4(String text) {
        content.append("#### ").append(text).append("\n\n");
        return this;
    }

    /**
     * 添加五级标题
     *
     * @param text 标题内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder h5(String text) {
        content.append("##### ").append(text).append("\n\n");
        return this;
    }

    /**
     * 添加六级标题
     *
     * @param text 标题内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder h6(String text) {
        content.append("###### ").append(text).append("\n\n");
        return this;
    }

    // ==================== 段落和换行 ====================

    /**
     * 添加段落
     *
     * @param text 段落内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder paragraph(String text) {
        content.append(text).append("\n\n");
        return this;
    }

    /**
     * 添加换行
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder br() {
        content.append("  \n");
        return this;
    }

    /**
     * 添加水平分割线
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder hr() {
        content.append("\n---\n\n");
        return this;
    }

    // ==================== 列表 ====================

    /**
     * 开始无序列表
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder beginUnorderedList() {
        return this;
    }

    /**
     * 添加无序列表项
     *
     * @param text 列表项内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder unorderedListItem(String text) {
        content.append("- ").append(text).append("\n");
        return this;
    }

    /**
     * 添加带缩进的无序列表项
     *
     * @param text  列表项内容
     * @param level 缩进级别（1-3）
     * @return 当前构建器实例
     */
    public MarkdownBuilder unorderedListItem(String text, int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < Math.max(0, level - 1); i++) {
            indent.append("  ");
        }
        content.append(indent.toString()).append("- ").append(text).append("\n");
        return this;
    }

    /**
     * 结束无序列表
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder endUnorderedList() {
        content.append("\n");
        return this;
    }

    /**
     * 开始有序列表
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder beginOrderedList() {
        return this;
    }

    /**
     * 添加有序列表项
     *
     * @param text 列表项内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder orderedListItem(String text) {
        content.append("1. ").append(text).append("\n");
        return this;
    }

    /**
     * 添加带编号的有序列表项
     *
     * @param text   列表项内容
     * @param number 编号
     * @return 当前构建器实例
     */
    public MarkdownBuilder orderedListItem(String text, int number) {
        content.append(number).append(". ").append(text).append("\n");
        return this;
    }

    /**
     * 添加带缩进的有序列表项
     *
     * @param text   列表项内容
     * @param level  缩进级别（1-3）
     * @param number 编号
     * @return 当前构建器实例
     */
    public MarkdownBuilder orderedListItem(String text, int level, int number) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < Math.max(0, level - 1); i++) {
            indent.append("   ");
        }
        content.append(indent.toString()).append(number).append(". ").append(text).append("\n");
        return this;
    }

    /**
     * 结束有序列表
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder endOrderedList() {
        content.append("\n");
        return this;
    }

    /**
     * 添加任务列表项（已完成）
     *
     * @param text 任务内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder taskCompleted(String text) {
        content.append("- [x] ").append(text).append("\n");
        return this;
    }

    /**
     * 添加任务列表项（未完成）
     *
     * @param text 任务内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder taskPending(String text) {
        content.append("- [ ] ").append(text).append("\n");
        return this;
    }

    // ==================== 链接和图片 ====================

    /**
     * 添加链接
     *
     * @param text 链接显示文本
     * @param url  链接地址
     * @return 当前构建器实例
     */
    public MarkdownBuilder link(String text, String url) {
        content.append("[").append(text).append("](").append(url).append(")");
        return this;
    }

    /**
     * 添加带标题的链接
     *
     * @param text  链接显示文本
     * @param url   链接地址
     * @param title 链接标题
     * @return 当前构建器实例
     */
    public MarkdownBuilder linkWithTitle(String text, String url, String title) {
        content.append("[").append(text).append("](").append(url).append(" \"").append(title).append("\")");
        return this;
    }

    /**
     * 添加引用链接
     *
     * @param text      引用文本
     * @param reference 引用标识
     * @return 当前构建器实例
     */
    public MarkdownBuilder referenceLink(String text, String reference) {
        content.append("[").append(text).append("][").append(reference).append("]");
        return this;
    }

    /**
     * 定义引用链接
     *
     * @param reference 引用标识
     * @param url       链接地址
     * @return 当前构建器实例
     */
    public MarkdownBuilder defineReference(String reference, String url) {
        content.append("[").append(reference).append("]: ").append(url).append("\n");
        return this;
    }

    /**
     * 添加图片
     *
     * @param alt 替代文本
     * @param url 图片地址
     * @return 当前构建器实例
     */
    public MarkdownBuilder image(String alt, String url) {
        content.append("![").append(alt).append("](").append(url).append(")");
        return this;
    }

    /**
     * 添加带标题的图片
     *
     * @param alt   替代文本
     * @param url   图片地址
     * @param title 图片标题
     * @return 当前构建器实例
     */
    public MarkdownBuilder imageWithTitle(String alt, String url, String title) {
        content.append("![").append(alt).append("](").append(url).append(" \"").append(title).append("\")");
        return this;
    }

    // ==================== 代码 ====================

    /**
     * 添加行内代码
     *
     * @param code 代码内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder inlineCode(String code) {
        content.append("`").append(code).append("`");
        return this;
    }

    /**
     * 开始代码块
     *
     * @param language 编程语言（可选）
     * @return 当前构建器实例
     */
    public MarkdownBuilder beginCodeBlock(String language) {
        content.append("```").append(language).append("\n");
        return this;
    }

    /**
     * 开始代码块（无语言指定）
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder beginCodeBlock() {
        return beginCodeBlock("");
    }

    /**
     * 添加代码行
     *
     * @param code 代码内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder codeLine(String code) {
        content.append(code).append("\n");
        return this;
    }

    /**
     * 结束代码块
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder endCodeBlock() {
        content.append("```\n\n");
        return this;
    }

    /**
     * 添加完整代码块
     *
     * @param language 编程语言
     * @param code     代码内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder codeBlock(String language, String code) {
        return beginCodeBlock(language).codeLine(code).endCodeBlock();
    }

    // ==================== 引用 ====================

    /**
     * 添加块引用
     *
     * @param text 引用内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder blockquote(String text) {
        content.append("> ").append(text).append("\n\n");
        return this;
    }

    /**
     * 添加多行块引用
     *
     * @param lines 引用行数组
     * @return 当前构建器实例
     */
    public MarkdownBuilder blockquote(String... lines) {
        for (String line : lines) {
            content.append("> ").append(line).append("\n");
        }
        content.append("\n");
        return this;
    }

    /**
     * 添加嵌套块引用
     *
     * @param level 嵌套级别
     * @param text  引用内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder nestedBlockquote(int level, String text) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < level; i++) {
            prefix.append(">");
        }
        prefix.append(" ");
        content.append(prefix.toString()).append(text).append("\n\n");
        return this;
    }

    // ==================== 表格 ====================

    /**
     * 开始表格
     *
     * @param headers 表头数组
     * @return 当前构建器实例
     */
    public MarkdownBuilder beginTable(String... headers) {
        tableHeaders.clear();
        tableRows.clear();
        for (String header : headers) {
            tableHeaders.add(header);
        }
        return this;
    }

    /**
     * 添加表格行
     *
     * @param cells 单元格数据
     * @return 当前构建器实例
     */
    public MarkdownBuilder tableRow(String... cells) {
        List<String> row = new ArrayList<>();
        for (String cell : cells) {
            row.add(cell != null ? cell : "");
        }
        tableRows.add(row);
        return this;
    }

    /**
     * 结束表格并生成
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder endTable() {
        if (tableHeaders.isEmpty()) {
            return this;
        }

        // 添加表头
        StringJoiner headerRow = new StringJoiner(" | ");
        StringJoiner separatorRow = new StringJoiner(" | ");

        for (String header : tableHeaders) {
            headerRow.add(header);
            separatorRow.add("---");
        }

        content.append("| ").append(headerRow.toString()).append(" |\n");
        content.append("| ").append(separatorRow.toString()).append(" |\n");

        // 添加数据行
        for (List<String> row : tableRows) {
            StringJoiner dataRow = new StringJoiner(" | ");
            for (int i = 0; i < tableHeaders.size(); i++) {
                String cell = i < row.size() ? row.get(i) : "";
                dataRow.add(cell);
            }
            content.append("| ").append(dataRow.toString()).append(" |\n");
        }

        content.append("\n");
        return this;
    }

    /**
     * 添加完整表格
     *
     * @param headers 表头数组
     * @param rows    数据行数组
     * @return 当前构建器实例
     */
    public MarkdownBuilder table(String[] headers, String[][] rows) {
        beginTable(headers);
        for (String[] row : rows) {
            tableRow(row);
        }
        return endTable();
    }

    // ==================== HTML元素 ====================

    /**
     * 添加HTML标签
     *
     * @param tag     标签名
     * @param content 内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder htmlTag(String tag, String content) {
        this.content.append("<").append(tag).append(">")
                .append(content)
                .append("</").append(tag).append(">");
        return this;
    }

    /**
     * 添加HTML标签（带属性）
     *
     * @param tag        标签名
     * @param attributes 属性字符串
     * @param content    内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder htmlTag(String tag, String attributes, String content) {
        this.content.append("<").append(tag).append(" ").append(attributes).append(">")
                .append(content)
                .append("</").append(tag).append(">");
        return this;
    }

    /**
     * 添加HTML注释
     *
     * @param comment 注释内容
     * @return 当前构建器实例
     */
    public MarkdownBuilder htmlComment(String comment) {
        content.append("<!-- ").append(comment).append(" -->\n");
        return this;
    }

    // ==================== 特殊符号 ====================

    /**
     * 添加转义字符
     *
     * @param character 需要转义的字符
     * @return 当前构建器实例
     */
    public MarkdownBuilder escape(char character) {
        content.append("\\").append(character);
        return this;
    }

    /**
     * 添加转义字符串
     *
     * @param text 需要转义的文本
     * @return 当前构建器实例
     */
    public MarkdownBuilder escape(String text) {
        for (char c : text.toCharArray()) {
            if ("\\`*_{}[]()#+-.!".indexOf(c) != -1) {
                content.append("\\");
            }
            content.append(c);
        }
        return this;
    }

    // ==================== 构建和输出 ====================

    /**
     * 获取构建的Markdown内容
     *
     * @return Markdown字符串
     */
    public String build() {
        return content.toString();
    }

    /**
     * 清空构建器内容
     *
     * @return 当前构建器实例
     */
    public MarkdownBuilder clear() {
        content.setLength(0);
        tableHeaders.clear();
        tableRows.clear();
        return this;
    }

    /**
     * 获取内容长度
     *
     * @return 内容长度
     */
    public int length() {
        return content.length();
    }

    /**
     * 检查是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return content.length() == 0;
    }

    @Override
    public String toString() {
        return build();
    }
}