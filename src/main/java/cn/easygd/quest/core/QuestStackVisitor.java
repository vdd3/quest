package cn.easygd.quest.core;

import cn.easygd.quest.runtime.module.StackModule;
import cn.easygd.quest.runtime.statement.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VD
 */
public class QuestStackVisitor extends QuestStatementVisitor<StackModule> {

    /**
     * stack module
     */
    private final StackModule stackModule;

    public QuestStackVisitor() {
        stackModule = new StackModule();
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitType(QuestParser.TypeContext ctx) {
        TypeCodeStatement typeCodeStatement = new TypeCodeStatement();
        typeCodeStatement.add(ctx.getText());
        stackModule.setCodeStatement(typeCodeStatement);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitAssignmentExpr(QuestParser.AssignmentExprContext ctx) {
        return super.visitAssignmentExpr(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitPrefixExpr(QuestParser.PrefixExprContext ctx) {
        return super.visitPrefixExpr(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitArrayAccessExpr(QuestParser.ArrayAccessExprContext ctx) {
        List<CodeStatement> statementList = ctx.children.stream()
                .map(this::convertTree)
                .collect(Collectors.toList());

        ArrayExprCodeStatement statement = new ArrayExprCodeStatement();
        statement.addAll(statementList);
        stackModule.setCodeStatement(statement);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitPrimaryExpr(QuestParser.PrimaryExprContext ctx) {
        PrimaryExprCodeStatement primaryExprCodeStatement = new PrimaryExprCodeStatement();
        primaryExprCodeStatement.add(ctx.getText());
        stackModule.setCodeStatement(primaryExprCodeStatement);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitTernaryExpr(QuestParser.TernaryExprContext ctx) {
        return super.visitTernaryExpr(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitCastExpr(QuestParser.CastExprContext ctx) {
        return super.visitCastExpr(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitBinaryExpr(QuestParser.BinaryExprContext ctx) {
        return super.visitBinaryExpr(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitPostfixExpr(QuestParser.PostfixExprContext ctx) {
        List<CodeStatement> statementList = ctx.children.stream()
                .map(this::convertTree)
                .collect(Collectors.toList());

        PostFixExprCodeStatement statement = new PostFixExprCodeStatement();
        statement.addAll(statementList);
        stackModule.setCodeStatement(statement);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitCurrentMethodInvokeExpr(QuestParser.CurrentMethodInvokeExprContext ctx) {
        // function name
        String functionName = ctx.IDENTIFIER().getText();
        // arguments(this arguments shouldn't find info)
        String arguments = ctx.argumentList().getText();

        FunctionInvokeCodeStatement statement = new FunctionInvokeCodeStatement();
        statement.setFunctionName(functionName);
        statement.setArguments(arguments);
        stackModule.setCodeStatement(statement);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitClassMethodInvokeExpr(QuestParser.ClassMethodInvokeExprContext ctx) {
        // class name
        String className = ctx.IDENTIFIER(0).getText();
        // method name
        String methodName = ctx.IDENTIFIER(1).getText();
        // arguments(this arguments shouldn't find info)
        String arguments = ctx.argumentList().getText();

        ClassMethodInvokeExprCodeStatement statement = new ClassMethodInvokeExprCodeStatement();
        statement.setClassName(className);
        statement.setMethodName(methodName);
        statement.setArguments(arguments);
        stackModule.setCodeStatement(statement);
        return null;
    }

    /**
     * 获取模块
     *
     * @return 模块
     */
    @Override
    public StackModule getModule() {
        return stackModule;
    }
}
