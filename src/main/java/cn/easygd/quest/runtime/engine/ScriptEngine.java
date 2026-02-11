package cn.easygd.quest.runtime.engine;

import cn.easygd.quest.core.QuestLexer;
import cn.easygd.quest.core.QuestParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 脚本引擎
 * 使用ANTLR解析Quest脚本并生成AST
 */
public class ScriptEngine {
    private static final Logger logger = LoggerFactory.getLogger(ScriptEngine.class);

    /**
     * 解析脚本内容
     * @param scriptContent 脚本内容
     * @return 解析后的AST根节点
     * @throws RuntimeException 当解析失败时抛出
     */
    public ParseTree parseScript(String scriptContent) {
        try {
            logger.debug("开始解析脚本内容，长度: {} 字符", scriptContent.length());
            
            // 创建词法分析器
            QuestLexer lexer = new QuestLexer(CharStreams.fromString(scriptContent));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // 创建语法分析器
            QuestParser parser = new QuestParser(tokens);
            
            // 解析程序
            ParseTree tree = parser.program();
            
            logger.debug("脚本解析成功");
            return tree;
            
        } catch (Exception e) {
            logger.error("脚本解析失败", e);
            throw new RuntimeException("脚本解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 验证脚本语法
     * @param scriptContent 脚本内容
     * @return 是否语法正确
     */
    public boolean validateSyntax(String scriptContent) {
        try {
            parseScript(scriptContent);
            return true;
        } catch (Exception e) {
            logger.warn("脚本语法验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 格式化脚本内容（简单的预处理）
     * @param scriptContent 原始脚本内容
     * @return 格式化后的脚本内容
     */
    public String formatScript(String scriptContent) {
        if (scriptContent == null || scriptContent.trim().isEmpty()) {
            return scriptContent;
        }
        
        // 移除多余的空白行
        String formatted = scriptContent.replaceAll("\\n\\s*\\n\\s*\\n", "\n\n");
        
        // 确保文件以换行符结尾
        if (!formatted.endsWith("\n")) {
            formatted += "\n";
        }
        
        return formatted;
    }
}