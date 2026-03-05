package cn.easygd.quest.engine.runtime.executor;

import cn.easygd.quest.engine.runtime.module.EntityModule;
import cn.easygd.quest.engine.runtime.module.ServiceModule;
import cn.easygd.quest.engine.runtime.statement.service.FunctionCodeStatement;
import cn.easygd.quest.engine.runtime.statement.service.ProcessCodeStatement;
import cn.easygd.quest.engine.utils.markdown.MarkdownInfo;
import cn.easygd.quest.engine.utils.markdown.ProgramMarkdownInfo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;

/**
 * @author VD
 */
public class ProgramQuestScriptExecutor extends QuestScriptExecutor {

    /**
     * 执行
     *
     * @return markdown
     */
    @Override
    public MarkdownInfo execute() {
        if (CollectionUtils.isEmpty(modules)) {
            return null;
        }

        ProgramMarkdownInfo markdownInfo = new ProgramMarkdownInfo();

        // filter entity
        modules.stream().filter(module -> module instanceof EntityModule)
                .forEach(module -> {

                });

        // filter service
        modules.stream().filter(module -> module instanceof ServiceModule)
                .forEach(module -> {
                    ServiceModule serviceModule = (ServiceModule) module;

                    // process
                    Map<String, ProcessCodeStatement> processStatementMap = serviceModule.getProcessStatementMap();

                    // function
                    Map<String, FunctionCodeStatement> functionStatementMap = serviceModule.getFunctionStatementMap();
                });


        return markdownInfo;
    }
}
