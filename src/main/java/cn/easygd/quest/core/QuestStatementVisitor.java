package cn.easygd.quest.core;

import cn.easygd.quest.runtime.module.QuestModule;
import cn.easygd.quest.runtime.module.StackModule;
import cn.easygd.quest.runtime.statement.CodeStatement;
import cn.easygd.quest.runtime.statement.TokenCodeStatement;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
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
           if (statement instanceof QuestParser.VariableStatementContext) {
               statement.children.stream().map(this::convertTree).collect(Collectors.toList());

               CodeStatement codeStatement = convertTree(statement);
               codeStatementList.add(codeStatement);
            } else if (statement instanceof QuestParser.IfStatementContext) {

            } else if (statement instanceof QuestParser.ForStatementContext) {

            } else if (statement instanceof QuestParser.WhileStatementContext) {

            }
        }

        return codeStatementList;
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
        String token = QuestParser.VOCABULARY.getSymbolicName(typeIndex);

        tokenCodeStatement.setToken(token);
        tokenCodeStatement.setValue(tokenTxt);

        return tokenCodeStatement;
    }
}
