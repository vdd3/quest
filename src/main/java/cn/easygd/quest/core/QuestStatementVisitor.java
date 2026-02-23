package cn.easygd.quest.core;

import cn.easygd.quest.runtime.module.QuestModule;
import cn.easygd.quest.runtime.module.StackModule;
import cn.easygd.quest.runtime.statement.CodeStatement;
import cn.easygd.quest.runtime.statement.TokenCodeStatement;
import cn.easygd.quest.runtime.statement.service.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author VD
 */
public abstract class QuestStatementVisitor<T extends QuestModule> extends QuestParserBaseVisitor<Void> {

    /**
     * 获取模块
     *
     * @return 模块
     */
    public abstract T getModule();

    /**
     * parse script core statement
     *
     * <ul>
     *     <li></li>
     * </ul>
     *
     * @param statementList statement list
     * @return code statement list
     */
    protected List<CodeStatement> parseCoreStatement(List<QuestParser.StatementContext> statementList) {
        List<CodeStatement> codeStatementList = new ArrayList<>();

        // build code statement
        for (QuestParser.StatementContext statement : statementList) {
            List<CodeStatement> codeStatements = statement.children
                    .stream()
                    .map(this::convertTree)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (statement instanceof QuestParser.VariableStatementContext) {
                VariableCodeStatement variableCodeStatement = new VariableCodeStatement();
                variableCodeStatement.addAll(codeStatements);
                codeStatementList.add(variableCodeStatement);
            } else if (statement instanceof QuestParser.IfStatementContext) {
                IfCodeStatement ifCodeStatement = new IfCodeStatement();
                ifCodeStatement.addAll(codeStatements);
                codeStatementList.add(ifCodeStatement);
            } else if (statement instanceof QuestParser.ForStatementContext) {
                // TODO 循环条件

                ForCodeStatement forCodeStatement = new ForCodeStatement();
                forCodeStatement.addAll(codeStatements);
            } else if (statement instanceof QuestParser.WhileStatementContext) {
                // 循环条件

                WhileCodeStatement whileCodeStatement = new WhileCodeStatement();
                whileCodeStatement.addAll(codeStatements);
                codeStatementList.add(whileCodeStatement);
            } else if (statement instanceof QuestParser.ExpressionStatementContext) {
                ExpressionCodeStatement expressionCodeStatement = new ExpressionCodeStatement();
                expressionCodeStatement.addAll(codeStatements);
                codeStatementList.add(expressionCodeStatement);
            } else if (statement instanceof QuestParser.ReturnStatementContext) {
                ReturnCodeStatement returnCodeStatement = new ReturnCodeStatement();
                returnCodeStatement.addAll(codeStatements);
                codeStatementList.add(returnCodeStatement);
            }
        }

        return codeStatementList;
    }

    /**
     * convert tree list to code statement list
     *
     * @param treeList tree list
     * @return code statement list
     */
    protected List<CodeStatement> convertTree(List<ParseTree> treeList) {
        return treeList.stream()
                .map(this::convertTree)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * convert tree to code statement
     *
     * @param tree parse tree
     * @return code statement
     */
    protected CodeStatement convertTree(ParseTree tree) {
        // token tree
        if (tree instanceof TerminalNode) {
            return buildTokenStatement((TerminalNode) tree);
        } else if (tree instanceof ParserRuleContext) {
            // rule tree
            ParserRuleContext ruleContext = (ParserRuleContext) tree;

            // parse stack
            QuestStackVisitor stackVisitor = new QuestStackVisitor();
            ruleContext.accept(stackVisitor);
            StackModule stackModule = stackVisitor.getModule();
            return stackModule.getCodeStatement();
        }
        return null;
    }

    /**
     * build token statement
     *
     * @param node terminal node
     * @return token statement
     */
    protected TokenCodeStatement buildTokenStatement(TerminalNode node) {
        TokenCodeStatement tokenCodeStatement = new TokenCodeStatement();

        // token text
        String tokenTxt = node.getText();
        // token
        int typeIndex = node.getSymbol().getType();

        // ignore BACKQUOTE
        if (QuestLexer.BACKQUOTE == typeIndex) {
            return null;
        }

        String token = QuestParser.VOCABULARY.getSymbolicName(typeIndex);

        tokenCodeStatement.setTokenIndex(typeIndex);
        tokenCodeStatement.setToken(token);
        tokenCodeStatement.setValue(tokenTxt);

        return tokenCodeStatement;
    }

    /**
     * get input name
     *
     * @param ctx parse tree
     * @return input name
     */
    protected TokenCodeStatement getInputName(ParserRuleContext ctx) {
        QuestStackVisitor visitor = new QuestStackVisitor();
        ctx.accept(visitor);
        CodeStatement codeStatement = visitor.getModule().getCodeStatement();
        return (TokenCodeStatement) codeStatement;
    }
}
