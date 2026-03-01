package cn.easygd.quest.engine.utils;

import cn.easygd.quest.engine.utils.markdown.ProgramMarkdownInfo;

/**
 * MarkdownBuilder使用示例
 * 展示各种Markdown语法的构建方法
 *
 * @author Quest Team
 * @since 1.0.0
 */
public class MarkdownBuilderExample {

    public static void main(String[] args) {
        ProgramMarkdownInfo markdownInfo = new ProgramMarkdownInfo();


        // 创建Markdown构建器实例
        MarkdownBuilder md = new MarkdownBuilder();

        // 构建完整的Markdown文档
        String markdownContent = md
                // 文档标题
                .h1("Quest项目文档")

                // 项目简介
                .h2("项目简介")
                .paragraph("Quest是一个开源的代码生成框架，允许用户通过编写脚本来自动生成Java代码块。")

                // 功能特性（无序列表）
                .h2("功能特性")
                .beginUnorderedList()
                .unorderedListItem("自动注解扫描 - 自动扫描项目中带有@AgentComment注解的类和@AgentMethod注解的方法")
                .unorderedListItem("脚本解析 - 使用ANTLR解析自定义的Quest脚本语言")
                .unorderedListItem("代码生成 - 将脚本转换为可执行的Java代码块")
                .unorderedListItem("Markdown导出 - 将生成的代码集中导出到Markdown文档中")
                .endUnorderedList()

                // 快速开始
//            .h2("快速开始")
//            .h3("1. 添加依赖")
//            .paragraph("在您的项目pom.xml中添加Quest依赖：")
//            .codeBlock("xml", """
//                <dependency>
//                    <groupId>cn.easygd</groupId>
//                    <artifactId>quest</artifactId>
//                    <version>1.0-SNAPSHOT</version>
//                </dependency>
//                """)
//
//            .h3("2. 标记您的类和方法")
//            .codeBlock("java", """
//                import cn.easygd.quest.runtime.annotation.AgentComment;
//                import cn.easygd.quest.runtime.annotation.AgentMethod;
//
//                @AgentComment
//                public class UserService {
//
//                    @AgentMethod
//                    public String getUserById(Long id) {
//                        return "User-" + id;
//                    }
//                }
//                """)

                // 使用说明（有序列表）
                .h2("使用说明")
                .beginOrderedList()
                .orderedListItem("编写Quest脚本文件")
                .orderedListItem("配置扫描路径")
                .orderedListItem("运行代码生成")
                .orderedListItem("查看生成结果")
                .endOrderedList()

                // 任务列表
                .h2("开发计划")
                .taskCompleted("核心框架搭建")
                .taskCompleted("ANTLR语法解析")
                .taskPending("更多代码模板支持")
                .taskPending("Web界面开发")

                // API参考（表格）
                .h2("API参考")
                .beginTable("方法名", "参数", "返回值", "说明")
                .tableRow("h1", "String text", "MarkdownBuilder", "添加一级标题")
                .tableRow("bold", "String text", "MarkdownBuilder", "添加粗体文本")
                .tableRow("italic", "String text", "MarkdownBuilder", "添加斜体文本")
                .tableRow("link", "String text, String url", "MarkdownBuilder", "添加链接")
                .tableRow("codeBlock", "String language, String code", "MarkdownBuilder", "添加代码块")
                .endTable()

                // 代码示例
                .h2("代码示例")
                .paragraph("以下是一个简单的Quest脚本示例：")
//            .codeBlock("quest", """
//                @kind service;
//                @business `订单业务`;
//                process createOrder {
//                    String orderId = "ORD-001";
//                    Order order = 订单服务.创建订单(orderId);
//                }
//                """)
//
//            // 引用
//            .h2("注意事项")
//            .blockquote("""
//                注意：在使用Quest框架时，请确保：
//                1. 正确配置注解扫描路径
//                2. 遵循脚本编写规范
//                3. 及时查看生成日志
//                """)

                // 链接
                .h2("相关链接")
                .paragraph("了解更多请访问：")
                .link("GitHub仓库", "https://github.com/example/quest")
                .text(" | ")
                .link("官方文档", "https://docs.example.com/quest")

                // 结束
                .hr()
                .paragraph("© 2026 Quest Team. All rights reserved.")

                .build();

        // 输出生成的Markdown
        System.out.println(markdownContent);

    }
}