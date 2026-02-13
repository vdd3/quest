package cn.easygd.quest.runtime.module;

import cn.easygd.quest.runtime.statement.ProcessCodeStatement;

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
     * put process statement
     *
     * @param processName      process name
     * @param processStatement process statement
     */
    public void putProcessStatement(String processName, ProcessCodeStatement processStatement) {
        processStatementMap.put(processName, processStatement);
    }

    public void setBizModule(String bizModule) {
        this.bizModule = bizModule;
    }
}
