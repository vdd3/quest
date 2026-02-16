package cn.easygd.quest.core;

import cn.easygd.quest.runtime.module.ServiceModule;
import cn.easygd.quest.runtime.statement.CodeStatement;
import cn.easygd.quest.runtime.statement.ProcessCodeStatement;

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
        String bizModule = ctx.IDENTIFIER().getText();
        serviceModule.setBizModule(bizModule);
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

        String processName = ctx.IDENTIFIER().getText();
        processCodeStatement.setName(processName);
        serviceModule.putProcessStatement(processName, processCodeStatement);
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
