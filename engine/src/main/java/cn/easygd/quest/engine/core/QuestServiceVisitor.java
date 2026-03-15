package cn.easygd.quest.engine.core;

import cn.easygd.quest.engine.runtime.module.ServiceModule;
import cn.easygd.quest.engine.runtime.statement.CodeStatement;
import cn.easygd.quest.engine.runtime.statement.TokenCodeStatement;
import cn.easygd.quest.engine.runtime.statement.service.FunctionCodeStatement;
import cn.easygd.quest.engine.runtime.statement.service.ProcessCodeStatement;

import java.util.List;

/**
 * @author VD
 */
public class QuestServiceVisitor extends QuestStatementVisitor<ServiceModule> {

    /**
     * 模块
     */
    private final ServiceModule serviceModule;

    public QuestServiceVisitor() {
        serviceModule = new ServiceModule();
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
    public Void visitBizModule(QuestParser.BizModuleContext ctx) {
        TokenCodeStatement inputName = getInputTxt(ctx.inputTxt());
        serviceModule.setBizModule(inputName.getValue());
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
    public Void visitProcessModule(QuestParser.ProcessModuleContext ctx) {
        List<QuestParser.StatementContext> statement = ctx.statement();

        ProcessCodeStatement processCodeStatement = new ProcessCodeStatement();

        List<CodeStatement> codeStatements = parseCoreStatement(statement);

        TokenCodeStatement inputName = getInputTxt(ctx.inputTxt());
        String processName = inputName.getValue();
        processCodeStatement.setName(processName);
        processCodeStatement.addAll(codeStatements);
        serviceModule.putProcessStatement(processName, processCodeStatement);
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
    public Void visitFunctionModule(QuestParser.FunctionModuleContext ctx) {
        List<QuestParser.FunctionDefinitionContext> contextList = ctx.functionDefinition();
        for (QuestParser.FunctionDefinitionContext context : contextList) {
            FunctionCodeStatement functionCodeStatement = new FunctionCodeStatement();

            // return type
            String returnType = context.type().getText();
            // usageLevel
            String usageLevel = context.usageLevel().getText();
            // function name
            TokenCodeStatement inputName = getInputTxt(context.inputTxt());
            String functionName = inputName.getValue();
            // function parameter
            String parameters = context.parameterList().getText();
            // function content
            List<CodeStatement> statementList = parseCoreStatement(context.statement());
            functionCodeStatement.addAll(statementList);
            functionCodeStatement.setName(functionName);
            functionCodeStatement.setParameters(parameters);
            functionCodeStatement.setUsageLevel(usageLevel);
            functionCodeStatement.setReturnType(returnType);
            serviceModule.putFunctionStatement(functionName,functionCodeStatement);
        }
        return null;
    }

    /**
     * 获取模块
     *
     * @return 模块
     */
    @Override
    public ServiceModule getModule() {
        return serviceModule;
    }
}
