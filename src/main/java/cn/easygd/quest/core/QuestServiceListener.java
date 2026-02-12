package cn.easygd.quest.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VD
 */
public class QuestServiceListener extends QuestParserBaseListener{

    private List<String> processList = new ArrayList<>();
    private List<String> useStatements = new ArrayList<>();
    private boolean inProcessModule = false;

    @Override
    public void enterProcessModule(QuestParser.ProcessModuleContext ctx) {
        inProcessModule = true;

        ctx.statement().forEach(statement -> {
            if (statement.useStatement() != null) {
                String useText = statement.useStatement().getText();
                useStatements.add(useText);
                processList.add("use语句: " + useText);
                System.out.println("发现use语句: " + useText);
            }
        });

        processList.add("进入process模块");
        System.out.println("进入process模块");
    }

    @Override
    public void exitProcessModule(QuestParser.ProcessModuleContext ctx) {
        inProcessModule = false;
        processList.add("退出process模块");
        System.out.println("退出process模块");
    }

    @Override
    public void enterUseStatement(QuestParser.UseStatementContext ctx) {
        String text = ctx.getStart().getText();


        String useText = ctx.getText();
        useStatements.add(useText);
        if (inProcessModule) {
            processList.add("use语句: " + useText);
        }
        System.out.println("发现use语句: " + useText);
        
        // 调试信息
        System.out.println("  USE token: " + (ctx.USE() != null ? ctx.USE().getText() : "null"));
        System.out.println("  IDENTIFIERs数量: " + ctx.IDENTIFIER().size());
        System.out.println("  参数列表: " + (ctx.argumentList() != null ? ctx.argumentList().getText() : "无"));
    }

    public List<String> getProcessList() {
        return processList;
    }

    public List<String> getUseStatements() {
        return useStatements;
    }

    public boolean isInProcessModule() {
        return inProcessModule;
    }
}
