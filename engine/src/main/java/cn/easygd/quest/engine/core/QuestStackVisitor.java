package cn.easygd.quest.engine.core;

import cn.easygd.quest.engine.runtime.module.StackModule;
import cn.easygd.quest.engine.runtime.statement.TokenCodeStatement;
import cn.easygd.quest.engine.runtime.statement.service.*;

import java.util.List;
import java.util.Objects;

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
    public Void visitBlock(QuestParser.BlockContext ctx) {
        BlockCodeStatement blockCodeStatement = new BlockCodeStatement();

        List<QuestParser.StatementContext> statement = ctx.statement();
        blockCodeStatement.addAll(parseCoreStatement(statement));
        stackModule.setCodeStatement(blockCodeStatement);
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
        String text = ctx.IDENTIFIER().getText();
        String assignmentOp = ctx.assignmentOperator().getText();
        ExpressionCodeStatement expr = (ExpressionCodeStatement) convertTree(ctx.expression());

        AssignmentExprCodeStatement statement = new AssignmentExprCodeStatement();
        statement.setVariable(text);
        statement.setAssignmentOperator(assignmentOp);
        statement.setExpr(expr);
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
    public Void visitPrefixExpr(QuestParser.PrefixExprContext ctx) {
        String prefixText = ctx.prefix.getText();
        ExpressionCodeStatement expr = (ExpressionCodeStatement) convertTree(ctx.expression());

        PrefixExprCodeStatement statement = new PrefixExprCodeStatement();
        statement.setPrefix(prefixText);
        statement.setExpr(expr);
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
    public Void visitArrayAccessExpr(QuestParser.ArrayAccessExprContext ctx) {
        ExpressionCodeStatement leftExpr = (ExpressionCodeStatement) convertTree(ctx.expression(0));
        ExpressionCodeStatement rightExpr = (ExpressionCodeStatement) convertTree(ctx.expression(1));

        ArrayExprCodeStatement statement = new ArrayExprCodeStatement();
        statement.setLeftExpr(leftExpr);
        statement.setRightExpr(rightExpr);
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
        PrimaryExprCodeStatement statement = new PrimaryExprCodeStatement();

        QuestParser.ExpressionContext expression = ctx.primary().expression();
        if (Objects.nonNull(expression)) {
            ExpressionCodeStatement expr = (ExpressionCodeStatement) convertTree(expression);
            statement.setPrimary(String.format("(%s)", expr.buildContent()));
        } else {
            statement.setPrimary(ctx.getText());
        }

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
    public Void visitNewExpr(QuestParser.NewExprContext ctx) {
        String classType = ctx.classType().getText();
        String arguments = ctx.parameterList().getText();

        NewExprCodeStatement statement = new NewExprCodeStatement();
        statement.setClassType(classType);
        statement.setArguments(arguments);
        stackModule.setCodeStatement(statement);
        return super.visitNewExpr(ctx);
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
        ExpressionCodeStatement conditionExpr = (ExpressionCodeStatement) convertTree(ctx.expression(0));
        ExpressionCodeStatement trueExpr = (ExpressionCodeStatement) convertTree(ctx.expression(1));
        ExpressionCodeStatement falseExpr = (ExpressionCodeStatement) convertTree(ctx.expression(2));

        TernaryExprCodeStatement statement = new TernaryExprCodeStatement();
        statement.setCondition(conditionExpr);
        statement.setTrueExpr(trueExpr);
        statement.setFalseExpr(falseExpr);
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
    public Void visitCastExpr(QuestParser.CastExprContext ctx) {
        String type = ctx.type().getText();
        ExpressionCodeStatement expr = (ExpressionCodeStatement) convertTree(ctx.expression());

        CastExprCodeStatement statement = new CastExprCodeStatement();
        statement.setCastType(type);
        statement.setExpr(expr);
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
    public Void visitBinaryExpr(QuestParser.BinaryExprContext ctx) {
        ExpressionCodeStatement leftExpr = (ExpressionCodeStatement) convertTree(ctx.expression(0));
        ExpressionCodeStatement rightExpr = (ExpressionCodeStatement) convertTree(ctx.expression(1));
        String binaryOp = ctx.binaryOp().getText();

        BinaryExprCodeStatement statement = new BinaryExprCodeStatement();
        statement.setLeftExpr(leftExpr);
        statement.setRightExpr(rightExpr);
        statement.setBinaryOperator(binaryOp);
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
    public Void visitPostfixExpr(QuestParser.PostfixExprContext ctx) {
        String postfixText = ctx.postfix.getText();
        ExpressionCodeStatement expr = (ExpressionCodeStatement) convertTree(ctx.expression());

        PostFixExprCodeStatement statement = new PostFixExprCodeStatement();
        statement.setPostfix(postfixText);
        statement.setExpr(expr);
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
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public Void visitBinaryOp(QuestParser.BinaryOpContext ctx) {
        TokenCodeStatement codeStatement = (TokenCodeStatement) ctx.children.stream().findFirst()
                .map(this::convertTree)
                .orElse(null);

        stackModule.setCodeStatement(codeStatement);
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
    public Void visitAssignmentOperator(QuestParser.AssignmentOperatorContext ctx) {
        TokenCodeStatement codeStatement = (TokenCodeStatement) ctx.children.stream().findFirst()
                .map(this::convertTree)
                .orElse(null);

        stackModule.setCodeStatement(codeStatement);
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
    public Void visitForControlExpr(QuestParser.ForControlExprContext ctx) {
        ForControlExprCodeStatement statement = new ForControlExprCodeStatement();
        ExpressionCodeStatement leftExpr = (ExpressionCodeStatement) convertTree(ctx.expression(0));
        ExpressionCodeStatement rightExpr = (ExpressionCodeStatement) convertTree(ctx.expression(1));
        ExpressionCodeStatement conditionExpr = (ExpressionCodeStatement) convertTree(ctx.expression(2));
        statement.setLeftExpr(leftExpr);
        statement.setRightExpr(rightExpr);
        statement.setConditionExpr(conditionExpr);
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
    public Void visitForVariableDeclaration(QuestParser.ForVariableDeclarationContext ctx) {
        ForVariableDeclCodeStatement statement = new ForVariableDeclCodeStatement();
        statement.setVariableName(ctx.IDENTIFIER(0).getText());
        statement.setVariableType(ctx.classType().getText());
        statement.setVariableValue(ctx.IDENTIFIER(1).getText());
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
