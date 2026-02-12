package cn.easygd.quest.core;

import cn.easygd.quest.core.enums.KindType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author VD
 */
public class QuestListenerManager {

    private static final Map<KindType, QuestParserListener> LISTENER_MAP = new ConcurrentHashMap<>(2);

    static {
        LISTENER_MAP.put(KindType.SERVICE, new QuestServiceListener());
        LISTENER_MAP.put(KindType.PRD, new QuestPrdListener());
    }

    public static QuestParserListener getListener(KindType kind) {
        return LISTENER_MAP.get(kind);
    }
}
