package cn.easygd.quest.engine.utils;

import cn.easygd.quest.api.annotation.QuestComponent;
import cn.easygd.quest.engine.core.QuestLexer;
import cn.easygd.quest.engine.core.QuestParser;
import cn.easygd.quest.engine.core.QuestServiceVisitor;
import cn.easygd.quest.engine.runtime.module.ServiceModule;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Quest组件解析器
 * 结合注解扫描和脚本解析功能
 *
 * @author VD
 */
public class ComponentParser {

    /**
     * 组件信息封装类
     */
    public static class ComponentInfo {
        private String className;
        private String alias;
        private String description;
        private Map<String, MethodInfo> methods;
        private ServiceModule serviceModule;

        public ComponentInfo(String className, String alias, String description) {
            this.className = className;
            this.alias = alias;
            this.description = description;
            this.methods = new HashMap<>();
        }

        // Getters and Setters
        public String getClassName() {
            return className;
        }

        public String getAlias() {
            return alias;
        }

        public String getDescription() {
            return description;
        }

        public Map<String, MethodInfo> getMethods() {
            return methods;
        }

        public ServiceModule getServiceModule() {
            return serviceModule;
        }

        public void setServiceModule(ServiceModule serviceModule) {
            this.serviceModule = serviceModule;
        }

        @Override
        public String toString() {
            return String.format("ComponentInfo{className='%s', alias='%s', description='%s', methods=%d}",
                    className, alias, description, methods.size());
        }
    }

    /**
     * 方法信息封装类
     */
    public static class MethodInfo {
        private String methodName;
        private String alias;
        private String description;
        private String scriptContent;

        public MethodInfo(String methodName, String alias, String description) {
            this.methodName = methodName;
            this.alias = alias;
            this.description = description;
        }

        // Getters and Setters
        public String getMethodName() {
            return methodName;
        }

        public String getAlias() {
            return alias;
        }

        public String getDescription() {
            return description;
        }

        public String getScriptContent() {
            return scriptContent;
        }

        public void setScriptContent(String scriptContent) {
            this.scriptContent = scriptContent;
        }

        @Override
        public String toString() {
            return String.format("MethodInfo{methodName='%s', alias='%s', description='%s'}",
                    methodName, alias, description);
        }
    }

    /**
     * 解析带@QuestComponent注解的类
     *
     * @param componentClass 带注解的类
     * @return 组件信息对象
     */
    public static ComponentInfo parseComponent(Class<?> componentClass) {
        QuestComponent classAnnotation = componentClass.getAnnotation(QuestComponent.class);
        if (classAnnotation == null) {
            return null;
        }

        ComponentInfo componentInfo = new ComponentInfo(
                componentClass.getName(),
                classAnnotation.alis(),
                classAnnotation.description()
        );

        // 解析带注解的方法
        Method[] methods = componentClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(QuestComponent.class)) {
                QuestComponent methodAnnotation = method.getAnnotation(QuestComponent.class);
                MethodInfo methodInfo = new MethodInfo(
                        method.getName(),
                        methodAnnotation.alis(),
                        methodAnnotation.description()
                );

                // 尝试获取方法上的脚本内容（如果有）
                methodInfo.setScriptContent(extractScriptContent(method));

                componentInfo.getMethods().put(method.getName(), methodInfo);
            }
        }

        return componentInfo;
    }

    /**
     * 从方法中提取脚本内容
     * 这里可以根据实际需求实现不同的提取策略
     */
    private static String extractScriptContent(Method method) {
        // 示例：可以从方法注释、配置文件或其他地方提取脚本内容
        // 目前返回空字符串，需要根据具体实现填充
        return "";
    }

    /**
     * 解析组件脚本内容
     *
     * @param scriptContent 脚本内容
     * @return ServiceModule 对象
     */
    public static ServiceModule parseScript(String scriptContent) {
        try {
            // 创建词法分析器
            QuestLexer lexer = new QuestLexer(CharStreams.fromString(scriptContent));
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 创建语法分析器
            QuestParser parser = new QuestParser(tokens);

            // 解析脚本
            QuestParser.ScriptContext scriptCtx = parser.script();

            // 使用Visitor解析
            QuestServiceVisitor visitor = new QuestServiceVisitor();
            visitor.visit(scriptCtx);

            return visitor.getModule();
        } catch (Exception e) {
            System.err.println("解析脚本时发生错误: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 批量解析多个组件
     *
     * @param componentClasses 组件类列表
     * @return 组件信息映射
     */
    public static Map<String, ComponentInfo> parseComponents(List<Class<?>> componentClasses) {
        Map<String, ComponentInfo> componentMap = new HashMap<>();

        for (Class<?> componentClass : componentClasses) {
            ComponentInfo componentInfo = parseComponent(componentClass);
            if (componentInfo != null) {
                componentMap.put(componentInfo.getClassName(), componentInfo);

                // 如果有脚本内容，进行解析
                for (MethodInfo methodInfo : componentInfo.getMethods().values()) {
                    if (methodInfo.getScriptContent() != null && !methodInfo.getScriptContent().isEmpty()) {
                        ServiceModule serviceModule = parseScript(methodInfo.getScriptContent());
                        if (serviceModule != null) {
                            componentInfo.setServiceModule(serviceModule);
                        }
                    }
                }
            }
        }

        return componentMap;
    }

    /**
     * 生成组件报告
     *
     * @param components 组件信息映射
     * @return 报告字符串
     */
    public static String generateReport(Map<String, ComponentInfo> components) {
        StringBuilder report = new StringBuilder();
        report.append("# Quest组件扫描报告\n\n");
        report.append("## 扫描统计\n");
        report.append("- 总组件数: ").append(components.size()).append("\n\n");

        report.append("## 组件详情\n");

        for (ComponentInfo component : components.values()) {
            report.append("### ").append(component.getAlias()).append("\n");
            report.append("- **类名**: ").append(component.getClassName()).append("\n");
            report.append("- **描述**: ").append(component.getDescription()).append("\n");
            report.append("- **方法数**: ").append(component.getMethods().size()).append("\n\n");

            if (!component.getMethods().isEmpty()) {
                report.append("#### 方法列表:\n");
                for (MethodInfo method : component.getMethods().values()) {
                    report.append("- **").append(method.getAlias()).append("** (")
                            .append(method.getMethodName()).append(")\n");
                    report.append("  - 描述: ").append(method.getDescription()).append("\n");
                    if (method.getScriptContent() != null && !method.getScriptContent().isEmpty()) {
                        report.append("  - 脚本: ").append(method.getScriptContent()).append("\n");
                    }
                }
                report.append("\n");
            }

            // 显示解析后的模块信息
            if (component.getServiceModule() != null) {
                report.append("#### 解析结果:\n");
                report.append("- 业务模块: ").append(component.getServiceModule().getBizModule()).append("\n");
                report.append("- Process数量: ").append(component.getServiceModule().getProcessStatementMap().size()).append("\n");
                report.append("- Function数量: ").append(component.getServiceModule().getFunctionStatementMap().size()).append("\n\n");
            }
        }

        return report.toString();
    }

    /**
     * 主方法 - 示例用法
     */
    public static void main(String[] args) {
        // 1. 扫描组件
        List<Class<?>> componentClasses = ComponentScanner.scanComponents("cn.easygd.quest");

        // 2. 解析组件
        Map<String, ComponentInfo> components = parseComponents(componentClasses);

        // 3. 生成报告
        String report = generateReport(components);
        System.out.println(report);

        // 4. 可以进一步处理组件信息...
        for (ComponentInfo component : components.values()) {
            System.out.println("处理组件: " + component.getAlias());
            // 这里可以进行具体的业务处理
        }
    }
}