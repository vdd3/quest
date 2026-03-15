package cn.easygd.quest.engine.runtime.statement.service;

import cn.easygd.quest.engine.runtime.enums.StatementType;
import cn.easygd.quest.engine.runtime.statement.CollectCodeStatement;

/**
 * @author VD
 */
public class FunctionCodeStatement extends CollectCodeStatement {

    /**
     * 使用级别
     */
    private String usageLevel = "public";

    /**
     * 返回值类型
     */
    private String returnType;

    /**
     * 函数名
     */
    private String name;

    /**
     * 参数
     */
    private String parameters;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        return String.format("%s %s %s(%s) {\n%s\n}", usageLevel, returnType, name, parameters, super.buildContent());
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.FUNCTION;
    }

    public String getUsageLevel() {
        return usageLevel;
    }

    public void setUsageLevel(String usageLevel) {
        this.usageLevel = usageLevel;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
