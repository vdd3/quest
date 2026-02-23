package cn.easygd.quest.core;

import cn.easygd.quest.runtime.module.ServiceModule;
import cn.easygd.quest.runtime.statement.CodeStatement;
import cn.easygd.quest.runtime.statement.TokenCodeStatement;
import cn.easygd.quest.runtime.statement.service.FunctionCodeStatement;
import cn.easygd.quest.runtime.statement.service.ProcessCodeStatement;

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
        TokenCodeStatement inputName = getInputName(ctx.inputTxt());
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

        TokenCodeStatement inputName = getInputName(ctx.inputTxt());
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
            TokenCodeStatement inputName = getInputName(context.inputTxt());
            String functionName = inputName.getValue();
            FunctionCodeStatement functionCodeStatement = new FunctionCodeStatement();
            List<CodeStatement> statementList = parseCoreStatement(context.statement());
            functionCodeStatement.addAll(statementList);

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
