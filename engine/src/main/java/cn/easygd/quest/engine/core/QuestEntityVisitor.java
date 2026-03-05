package cn.easygd.quest.engine.core;

import cn.easygd.quest.engine.runtime.module.EntityModule;

/**
 * @author VD
 */
public class QuestEntityVisitor extends QuestStatementVisitor<EntityModule> {
    /**
     * 获取模块
     *
     * @return 模块
     */
    @Override
    public EntityModule getModule() {
        return null;
    }
}
