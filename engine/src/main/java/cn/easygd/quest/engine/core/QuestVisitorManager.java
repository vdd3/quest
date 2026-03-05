package cn.easygd.quest.engine.core;

import cn.easygd.quest.engine.core.enums.KindType;
import cn.easygd.quest.engine.runtime.module.QuestModule;

/**
 * @author VD
 */
public class QuestVisitorManager {

    /**
     * find visitor
     *
     * @param kindType kind type
     * @return visitor
     */
    public static QuestStatementVisitor<? extends QuestModule> findVisitor(KindType kindType) {
        switch (kindType) {
            case SERVICE:
                return new QuestServiceVisitor();
            case PRD:
                return new QuestPrdVisitor();
            case ENTITY:
                return new QuestEntityVisitor();
        }
        return null;
    }
}
