package cn.easygd.quest.core;

import cn.easygd.quest.core.enums.KindType;

/**
 * Kind信息提取器
 * 用于从解析树中提取用户的kind声明信息
 */
public class QuestKindVisitor extends QuestParserBaseVisitor<String> {

    /**
     * 访问kind声明节点，提取kind类型
     */
    @Override
    public String visitKindDeclaration(QuestParser.KindDeclarationContext ctx) {
        // 获取kind类型子节点
        QuestParser.KindTypeContext kindTypeCtx = ctx.kindType();
        if (kindTypeCtx != null) {
            return visitKindType(kindTypeCtx);
        }
        return null;
    }

    /**
     * 访问kind类型节点，获取具体类型值
     */
    @Override
    public String visitKindType(QuestParser.KindTypeContext ctx) {
        if (ctx.SERVICE() != null) {
            return "service";
        } else if (ctx.PRD() != null) {
            return "prd";
        }
        return null;
    }

    /**
     * 便捷方法：直接从脚本上下文提取kind
     */
    public KindType extractKind(QuestParser.ScriptContext scriptCtx) {
        if (scriptCtx.kindDeclaration() != null) {
            return KindType.getByCode(visitKindDeclaration(scriptCtx.kindDeclaration()));
        }
        return null;
    }
}