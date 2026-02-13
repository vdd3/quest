package cn.easygd.quest.core;

import cn.easygd.quest.runtime.module.PrdModule;

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
