package cn.easygd.quest.engine.runtime.executor;

import cn.easygd.quest.engine.runtime.module.QuestModule;
import cn.easygd.quest.engine.utils.markdown.MarkdownInfo;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author VD
 */
public abstract class QuestScriptExecutor {

    /**
     * 模块
     */
    protected final List<QuestModule> modules = Lists.newArrayList();

    /**
     * 执行
     *
     * @return markdown
     */
    public abstract MarkdownInfo execute();

    /**
     * 添加模块
     *
     * @param module 模块
     */
    public void addModule(QuestModule module) {
        modules.add(module);
    }
}
