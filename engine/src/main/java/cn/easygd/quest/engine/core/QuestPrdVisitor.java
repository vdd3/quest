package cn.easygd.quest.engine.core;

import cn.easygd.quest.engine.runtime.module.PrdModule;

/**
 * @author VD
 */
public class QuestPrdVisitor extends QuestStatementVisitor<PrdModule>{

    /**
     * 获取模块
     *
     * @return 模块
     */
    @Override
    public PrdModule getModule() {
        return null;
    }
}
