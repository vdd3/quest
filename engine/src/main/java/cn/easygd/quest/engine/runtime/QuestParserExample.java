package cn.easygd.quest.engine.runtime;

import cn.easygd.quest.engine.core.QuestHeaderVisitor;
import cn.easygd.quest.engine.core.QuestLexer;
import cn.easygd.quest.engine.core.QuestParser;
import cn.easygd.quest.engine.core.QuestServiceVisitor;
import cn.easygd.quest.engine.core.enums.KindType;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Quest脚本解析示例
 * 演示如何获取用户输入的kind信息
 */
public class QuestParserExample {

    public static void main(String[] args) {
        // 示例脚本内容
        String script1 = "@kind service;\n" +
                "@business `订单业务`;\n" +
                "process biz {\n" +
                "String id = \"1\";\n" +
                "User user = 用户服务.获取用户(id);\n" +
                "}";

//        String script2 = "@类型 产品\n需求 {\n    // 需求描述\n}";

        parseWithListener(script1);
//        parseWithListener(script2);
    }

    /**
     * 使用Listener模式解析并获取kind
     */
    public static void parseWithListener(String script) {
        try {
            System.out.println("开始解析脚本: " + script);
            
            // 创建词法分析器
            QuestLexer lexer = new QuestLexer(CharStreams.fromString(script));
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 创建语法分析器
            QuestParser parser = new QuestParser(tokens);
            
            // 先解析一次获取kind类型
            QuestParser.ScriptContext scriptCtx = parser.script();
            
            // 使用Visitor提取kind
            QuestHeaderVisitor extractor = new QuestHeaderVisitor();
            KindType kind = extractor.extractKind(scriptCtx);
            scriptCtx.accept(extractor);
            System.out.println("检测到kind类型: " + kind);

            QuestServiceVisitor questServiceVisitor = new QuestServiceVisitor();
            questServiceVisitor.visit(scriptCtx);

            System.out.println("-------");

            // 获取对应的listener
//            QuestParserListener baseListener = QuestVisitorManager.getListener(kind);
            
            // 如果是Service类型的listener，进行类型转换
//            QuestServiceVisitor serviceListener = null;
//            if (baseListener instanceof QuestServiceVisitor) {
//                serviceListener = (QuestServiceVisitor) baseListener;
//                System.out.println("成功获取QuestServiceListener实例");
//            } else {
//                System.out.println("警告: 获取的listener不是QuestServiceListener类型: " +
//                                 (baseListener != null ? baseListener.getClass().getName() : "null"));
//                return;
//            }
//
//            // 重要：重新创建完整的解析流程
//            QuestLexer lexer2 = new QuestLexer(CharStreams.fromString(script));
//            CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
//            QuestParser parser2 = new QuestParser(tokens2);
//
//            // 添加错误监听器以便调试
//            parser2.removeErrorListeners();
//            parser2.addErrorListener(new org.antlr.v4.runtime.BaseErrorListener() {
//                @Override
//                public void syntaxError(org.antlr.v4.runtime.Recognizer<?, ?> recognizer,
//                                      Object offendingSymbol,
//                                      int line,
//                                      int charPositionInLine,
//                                      String msg,
//                                      org.antlr.v4.runtime.RecognitionException e) {
//                    System.err.println("语法错误: " + msg + " (行:" + line + ", 列:" + charPositionInLine + ")");
//                }
//            });
//
//            // 添加listener
//            parser2.addParseListener(serviceListener);
//
//            // 重新解析以触发listener回调
//            System.out.println("开始第二次解析以触发listener回调...");
//            QuestParser.ScriptContext parsedScript = parser2.script();
//
//            System.out.println("\n=== 解析结果 ===");
//            System.out.println("Process模块列表:");
//            for (String item : serviceListener.getProcessList()) {
//                System.out.println("  - " + item);
//            }
//
//            System.out.println("\nUse语句列表:");
//            for (String useStmt : serviceListener.getUseStatements()) {
//                System.out.println("  - " + useStmt);
//            }
//
//            System.out.println("\n当前是否在Process模块中: " + serviceListener.isInProcessModule());
//
//            // 验证结果
//            if (serviceListener.getProcessList().isEmpty()) {
//                System.out.println("\n⚠️  警告: 没有检测到任何Process模块事件");
//            }
//            if (serviceListener.getUseStatements().isEmpty()) {
//                System.out.println("\n⚠️  警告: 没有检测到任何Use语句");
//            }
            
        } catch (Exception e) {
            System.err.println("解析错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}