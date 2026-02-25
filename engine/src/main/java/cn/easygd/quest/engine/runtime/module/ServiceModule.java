package cn.easygd.quest.engine.runtime.module;

import cn.easygd.quest.engine.runtime.statement.service.FunctionCodeStatement;
import cn.easygd.quest.engine.runtime.statement.service.ProcessCodeStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author VD
 */
public class ServiceModule implements QuestModule {

    /**
     * prd to service biz
     */
    private String bizModule;

    /**
     * process statement map
     */
    private Map<String, ProcessCodeStatement> processStatementMap = new HashMap<>(16);

    /**
     * function statement map
     */
    private Map<String, FunctionCodeStatement> functionStatementMap = new HashMap<>(16);

    /**
     * put process statement
     *
     * @param processName      process name
     * @param processStatement process statement
     */
    public void putProcessStatement(String processName, ProcessCodeStatement processStatement) {
        processStatementMap.put(processName, processStatement);
    }

    /**
     * put function statement
     *
     * @param functionName      function name
     * @param functionStatement function statement
     */
    public void putFunctionStatement(String functionName, FunctionCodeStatement functionStatement) {
        functionStatementMap.put(functionName, functionStatement);
    }

    public void setBizModule(String bizModule) {
        this.bizModule = bizModule;
    }

    public String getBizModule() {
        return bizModule;
    }
}
