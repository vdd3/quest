package cn.easygd.quest.runtime.generator;

import cn.easygd.quest.core.QuestParser;
import cn.easygd.quest.runtime.model.AnnotationInfo;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器
 * 将解析后的脚本AST转换为Java代码块
 */
public class CodeGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    
    private final Map<String, String> variableTypes = new HashMap<>();
    private final StringBuilder codeBuffer = new StringBuilder();
    private int indentLevel = 0;
    private int tempVarCounter = 0;

    /**
     * 生成Java代码块
     * @param parseTree 解析树
     * @param annotationInfo 注解信息
     * @return 生成的Java代码
     */
    public String generateCode(ParseTree parseTree, AnnotationInfo annotationInfo) {
        try {
            codeBuffer.setLength(0); // 清空缓冲区
            variableTypes.clear();
            indentLevel = 0;
            tempVarCounter = 0;
            
            logger.info("开始生成代码，目标类: {}", annotationInfo.getClassName());
            
            // 生成方法签名注释
            generateMethodSignatureComment(annotationInfo);
            
            // 生成代码主体
            generateCodeBlock(parseTree);
            
            String generatedCode = codeBuffer.toString();
            logger.info("代码生成完成，生成 {} 行代码", generatedCode.split("\n").length);
            
            return generatedCode;
            
        } catch (Exception e) {
            logger.error("代码生成失败", e);
            throw new RuntimeException("代码生成失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成方法签名注释
     */
    private void generateMethodSignatureComment(AnnotationInfo annotationInfo) {
        appendLine("// === 自动生成的代码块 ===");
        appendLine("// 目标类: " + annotationInfo.getClassName());
        appendLine("// 包名: " + annotationInfo.getPackageName());
        
        if (!annotationInfo.getAgentMethods().isEmpty()) {
            appendLine("// 可调用方法:");
            for (AnnotationInfo.MethodInfo method : annotationInfo.getAgentMethods()) {
                StringBuilder methodSig = new StringBuilder("//   ");
                methodSig.append(method.getReturnType().getSimpleName())
                         .append(" ")
                         .append(method.getMethodName())
                         .append("(");
                
                Class<?>[] params = method.getParameterTypes();
                for (int i = 0; i < params.length; i++) {
                    if (i > 0) methodSig.append(", ");
                    methodSig.append(params[i].getSimpleName()).append(" param").append(i + 1);
                }
                methodSig.append(")");
                
                appendLine(methodSig.toString());
            }
        }
        appendLine("");
    }

    /**
     * 生成代码块
     */
    private void generateCodeBlock(ParseTree tree) {
        if (tree == null) return;
        
        String ruleName = getRuleName(tree);
        
        switch (ruleName) {
            case "program":
                generateProgram(tree);
                break;
            case "blockStatements":
                generateBlockStatements(tree);
                break;
            case "blockStatement":
                generateBlockStatement(tree);
                break;
            case "localVariableDeclaration":
                generateLocalVariableDeclaration(tree);
                break;
            case "expression":
                generateExpression(tree);
                break;
            case "assignment":
                generateAssignment(tree);
                break;
            default:
                // 递归处理子节点
                for (int i = 0; i < tree.getChildCount(); i++) {
                    generateCodeBlock(tree.getChild(i));
                }
                break;
        }
    }

    /**
     * 生成程序代码
     */
    private void generateProgram(ParseTree tree) {
        for (int i = 0; i < tree.getChildCount(); i++) {
            ParseTree child = tree.getChild(i);
            if (child instanceof QuestParser.BlockStatementsContext) {
                generateBlockStatements(child);
            }
        }
    }

    /**
     * 生成语句块
     */
    private void generateBlockStatements(ParseTree tree) {
        for (int i = 0; i < tree.getChildCount(); i++) {
            ParseTree child = tree.getChild(i);
            if (child.getText().equals("{") || child.getText().equals("}")) {
                continue; // 跳过大括号
            }
            generateBlockStatement(child);
        }
    }

    /**
     * 生成单条语句
     */
    private void generateBlockStatement(ParseTree tree) {
        if (tree instanceof QuestParser.LocalVariableDeclarationStatementContext) {
            generateLocalVariableDeclaration(tree.getChild(0));
            appendLine(";");
        } else if (tree instanceof QuestParser.ExpressionStatementContext) {
            generateExpression(tree.getChild(0));
            appendLine(";");
        } else if (tree instanceof QuestParser.WhileStatementContext) {
            generateWhileStatement(tree);
        } else if (tree instanceof QuestParser.TraditionalForStatementContext) {
            generateForStatement(tree);
        } else {
            // 其他类型的语句，递归处理
            for (int i = 0; i < tree.getChildCount(); i++) {
                generateCodeBlock(tree.getChild(i));
            }
        }
    }

    /**
     * 生成局部变量声明
     */
    private void generateLocalVariableDeclaration(ParseTree tree) {
        if (tree.getChildCount() >= 2) {
            String type = tree.getChild(0).getText();
            String varName = tree.getChild(1).getText();
            
            variableTypes.put(varName, type);
            
            // 生成变量声明
            append(getIndent() + type + " " + varName);
            
            // 如果有初始化表达式
            if (tree.getChildCount() > 3 && tree.getChild(2).getText().equals("=")) {
                append(" = ");
                generateExpression(tree.getChild(3));
            }
        }
    }

    /**
     * 生成表达式
     */
    private void generateExpression(ParseTree tree) {
        if (tree instanceof TerminalNode) {
            append(tree.getText());
        } else if (tree.getChildCount() == 1) {
            generateExpression(tree.getChild(0));
        } else if (tree.getChildCount() == 3) {
            // 二元运算表达式
            generateExpression(tree.getChild(0)); // 左操作数
            append(" " + tree.getChild(1).getText() + " "); // 运算符
            generateExpression(tree.getChild(2)); // 右操作数
        } else {
            // 其他情况，递归处理所有子节点
            for (int i = 0; i < tree.getChildCount(); i++) {
                generateExpression(tree.getChild(i));
            }
        }
    }

    /**
     * 生成赋值语句
     */
    private void generateAssignment(ParseTree tree) {
        if (tree.getChildCount() >= 3) {
            String varName = tree.getChild(0).getText();
            String operator = tree.getChild(1).getText();
            ParseTree expr = tree.getChild(2);
            
            append(getIndent() + varName + " " + operator + " ");
            generateExpression(expr);
        }
    }

    /**
     * 生成while语句
     */
    private void generateWhileStatement(ParseTree tree) {
        appendLine(getIndent() + "while (");
        increaseIndent();
        
        // 生成条件表达式
        for (int i = 0; i < tree.getChildCount(); i++) {
            if (tree.getChild(i).getText().equals("(")) continue;
            if (tree.getChild(i).getText().equals(")")) break;
            if (tree.getChild(i).getText().equals("{")) break;
            generateCodeBlock(tree.getChild(i));
        }
        
        decreaseIndent();
        appendLine("");
        appendLine(getIndent() + ") {");
        
        increaseIndent();
        // 生成循环体
        for (int i = 0; i < tree.getChildCount(); i++) {
            if (tree.getChild(i).getText().equals("{")) {
                generateBlockStatements(tree.getChild(i + 1));
                break;
            }
        }
        decreaseIndent();
        appendLine(getIndent() + "}");
    }

    /**
     * 生成for语句
     */
    private void generateForStatement(ParseTree tree) {
        append(getIndent() + "for (");
        increaseIndent();
        
        // 生成for循环头
        boolean inHeader = false;
        for (int i = 0; i < tree.getChildCount(); i++) {
            String text = tree.getChild(i).getText();
            if (text.equals("(")) {
                inHeader = true;
                continue;
            }
            if (text.equals(")")) {
                inHeader = false;
                break;
            }
            if (text.equals("{")) break;
            
            if (inHeader) {
                generateCodeBlock(tree.getChild(i));
            }
        }
        
        decreaseIndent();
        appendLine("");
        appendLine(getIndent() + ") {");
        
        increaseIndent();
        // 生成循环体
        for (int i = 0; i < tree.getChildCount(); i++) {
            if (tree.getChild(i).getText().equals("{")) {
                generateBlockStatements(tree.getChild(i + 1));
                break;
            }
        }
        decreaseIndent();
        appendLine(getIndent() + "}");
    }

    /**
     * 辅助方法：获取缩进字符串
     */
    private String getIndent() {
        return "    ";
//        return "    ".r(Math.max(0, indentLevel));
    }

    /**
     * 增加缩进级别
     */
    private void increaseIndent() {
        indentLevel++;
    }

    /**
     * 减少缩进级别
     */
    private void decreaseIndent() {
        if (indentLevel > 0) {
            indentLevel--;
        }
    }

    /**
     * 添加一行代码
     */
    private void appendLine(String line) {
        codeBuffer.append(line).append("\n");
    }

    /**
     * 添加代码片段（不换行）
     */
    private void append(String text) {
        codeBuffer.append(text);
    }

    /**
     * 获取规则名称
     */
    private String getRuleName(ParseTree tree) {
        if (tree.getClass().getSimpleName().endsWith("Context")) {
            return tree.getClass().getSimpleName()
                      .replace("Context", "")
                      .substring(0, 1).toLowerCase() + 
                      tree.getClass().getSimpleName()
                      .replace("Context", "")
                      .substring(1);
        }
        return tree.getClass().getSimpleName();
    }

    /**
     * 生成临时变量名
     */
    private String generateTempVarName() {
        return "tempVar" + (++tempVarCounter);
    }
}