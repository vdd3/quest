package cn.easygd.quest.engine.utils;

import cn.easygd.quest.engine.core.QuestParser;
import cn.easygd.quest.engine.core.enums.KindType;

/**
 * @author VD
 */
public class ScriptKindHelper {

    /**
     * 获取脚本的Kind类型
     *
     * @param scriptCtx 脚本上下文
     * @return Kind类型
     */
    public static KindType getScriptKind(QuestParser.ScriptContext scriptCtx) {
        return KindType.getByCode(scriptCtx.kindDeclaration().kindType().getText());
    }
}
