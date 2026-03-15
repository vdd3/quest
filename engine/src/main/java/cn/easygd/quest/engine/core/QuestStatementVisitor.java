package cn.easygd.quest.engine.core;

import cn.easygd.quest.engine.runtime.module.QuestModule;
import cn.easygd.quest.engine.runtime.module.StackModule;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;
import cn.easygd.quest.engine.runtime.statement.TokenCodeStatement;
import cn.easygd.quest.engine.runtime.statement.service.*;
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
            if (statement instanceof QuestParser.VariableStatementContext) {
                QuestParser.VariableStatementContext variableStatement = (QuestParser.VariableStatementContext) statement;

                VariableCodeStatement variableCodeStatement = new VariableCodeStatement();
                String type = variableStatement.type().getText();
                String variableName = variableStatement.IDENTIFIER().getText();

                //  variable
                variableCodeStatement.setVariableType(type);
                variableCodeStatement.setVariableName(variableName);

                // expr
                QuestParser.ExpressionContext expression = variableStatement.expression();
                if (Objects.nonNull(expression)) {
                    variableCodeStatement.setExpr((ExpressionCodeStatement) convertTree(expression));
                }

                codeStatementList.add(variableCodeStatement);
            } else if (statement instanceof QuestParser.IfStatementContext) {
                QuestParser.IfStatementContext ifStatement = (QuestParser.IfStatementContext) statement;

                IfCodeStatement ifCodeStatement = new IfCodeStatement();
                // statement
                ExpressionCodeStatement condition = (ExpressionCodeStatement) convertTree(ifStatement.expression());
                // if  block
                BlockCodeStatement block = (BlockCodeStatement) convertTree(ifStatement.block(0));
                if (ifStatement.ELSE() != null) {
                    // else block
                    BlockCodeStatement elseBlock = (BlockCodeStatement) convertTree(ifStatement.block(1));
                    ifCodeStatement.setElseBlock(elseBlock);
                }
                ifCodeStatement.setCondition(condition);
                ifCodeStatement.setBlock(block);

                codeStatementList.add(ifCodeStatement);
            } else if (statement instanceof QuestParser.ForStatementContext) {
                QuestParser.ForStatementContext forStatement = (QuestParser.ForStatementContext) statement;
                ForCodeStatement forCodeStatement = new ForCodeStatement();

                // for control
                QuestParser.ForControlContext forControlContext = forStatement.forControl();
                ForControlCodeStatement forControl = (ForControlCodeStatement) convertTree(forControlContext);

                // for block
                BlockCodeStatement block = (BlockCodeStatement) convertTree(forStatement.block());

                forCodeStatement.setForControl(forControl);
                forCodeStatement.setForBlock(block);

                codeStatementList.add(forCodeStatement);
            } else if (statement instanceof QuestParser.WhileStatementContext) {
                QuestParser.WhileStatementContext whileStatement = (QuestParser.WhileStatementContext) statement;

                WhileCodeStatement whileCodeStatement = new WhileCodeStatement();
                // statement
                ExpressionCodeStatement condition = (ExpressionCodeStatement) convertTree(whileStatement.expression());
                // if  block
                BlockCodeStatement block = (BlockCodeStatement) convertTree(whileStatement.block());
                whileCodeStatement.setCondition(condition);
                whileCodeStatement.setBlock(block);

                codeStatementList.add(whileCodeStatement);
            } else if (statement instanceof QuestParser.ExpressionStatementContext) {
                ExprStrCodeStatement exprStrCodeStatement = new ExprStrCodeStatement();
                ExpressionCodeStatement expr = (ExpressionCodeStatement) convertTree(statement);
                exprStrCodeStatement.setExpr(expr);
                codeStatementList.add(exprStrCodeStatement);
            } else if (statement instanceof QuestParser.ReturnStatementContext) {
                QuestParser.ReturnStatementContext returnStatement = (QuestParser.ReturnStatementContext) statement;

                ReturnCodeStatement returnCodeStatement = new ReturnCodeStatement();
                // statement
                QuestParser.ExpressionContext expression = returnStatement.expression();
                if (Objects.nonNull(expression)) {
                    ExpressionCodeStatement returnValue = (ExpressionCodeStatement) convertTree(expression);
                    returnCodeStatement.setReturnValue(returnValue);
                }

                codeStatementList.add(returnCodeStatement);
            } else if (statement instanceof QuestParser.NoteStatementContext) {
                QuestParser.NoteStatementContext noteStatement = (QuestParser.NoteStatementContext) statement;

                NoteCodeStatement noteCodeStatement = new NoteCodeStatement();
                noteCodeStatement.setNote(getInputTxt(noteStatement.inputTxt()).getValue());
                codeStatementList.add(noteCodeStatement);
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
    protected TokenCodeStatement getInputTxt(QuestParser.InputTxtContext ctx) {
        String text = ctx.IDENTIFIER().getText();
        TokenCodeStatement txtCodeStatement = new TokenCodeStatement();
        txtCodeStatement.setToken(QuestParser.VOCABULARY.getSymbolicName(QuestLexer.IDENTIFIER));
        txtCodeStatement.setTokenIndex(QuestLexer.IDENTIFIER);
        txtCodeStatement.setValue(text);
        return txtCodeStatement;
    }
}
