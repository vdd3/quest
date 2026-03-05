package cn.easygd.quest.engine.runtime.executor;

import cn.easygd.quest.engine.core.enums.KindType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author VD
 */
public class QuestScriptExecutorManager {

    /**
     * 执行器
     */
    private static final Map<KindType, QuestScriptExecutor> EXECUTOR_MAP = Maps.newHashMapWithExpectedSize(2);

    static {
        ProgramQuestScriptExecutor programQuestScriptExecutor = new ProgramQuestScriptExecutor();
        EXECUTOR_MAP.put(KindType.SERVICE, programQuestScriptExecutor);
        EXECUTOR_MAP.put(KindType.ENTITY, programQuestScriptExecutor);
        EXECUTOR_MAP.put(KindType.PRD, null);
    }

    /**
     * 根据类型获取执行器
     *
     * @param kindType 类型
     * @return 执行器
     */
    public static QuestScriptExecutor findExecutor(KindType kindType) {
        return EXECUTOR_MAP.get(kindType);
    }

    /**
     * 获取所有执行器
     *
     * @return 执行器
     */
    public static List<QuestScriptExecutor> findAllExecutor() {
        return Lists.newArrayList(EXECUTOR_MAP.values());
    }
}
