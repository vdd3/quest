package cn.easygd.quest.example;

import cn.easygd.quest.engine.utils.ComponentParser;
import cn.easygd.quest.engine.utils.ComponentScanner;

import java.util.List;
import java.util.Map;

/**
 * Quest组件扫描和解析演示程序
 *
 * @author VD
 */
public class ComponentDemo {

    public static void main(String[] args) {
        System.out.println("=== Quest组件扫描和解析演示 ===\n");

        // 1. 扫描带@QuestComponent注解的类
        System.out.println("1. 开始扫描组件...");
        List<Class<?>> componentClasses = ComponentScanner.scanComponents("cn.easygd.quest");

        if (componentClasses.isEmpty()) {
            System.out.println("未找到任何带@QuestComponent注解的组件");
            return;
        }

        System.out.println("找到 " + componentClasses.size() + " 个组件类:");
        for (Class<?> clazz : componentClasses) {
            System.out.println("  - " + clazz.getName());
        }
        System.out.println();

        // 2. 解析组件详细信息
        System.out.println("2. 解析组件详细信息...");
        Map<String, ComponentParser.ComponentInfo> components =
                ComponentParser.parseComponents(componentClasses);

        // 3. 显示每个组件的详细信息
        System.out.println("3. 组件详细信息:");
        for (ComponentParser.ComponentInfo component : components.values()) {
            System.out.println("\n--- 组件: " + component.getAlias() + " ---");
            System.out.println("类名: " + component.getClassName());
            System.out.println("描述: " + component.getDescription());
            System.out.println("方法数量: " + component.getMethods().size());

            if (!component.getMethods().isEmpty()) {
                System.out.println("方法列表:");
                for (ComponentParser.MethodInfo method : component.getMethods().values()) {
                    System.out.println("  - " + method.getAlias() + " (" + method.getMethodName() + ")");
                    System.out.println("    描述: " + method.getDescription());
                    if (method.getScriptContent() != null && !method.getScriptContent().isEmpty()) {
                        System.out.println("    脚本内容: " + method.getScriptContent());
                    }
                }
            }
        }

        // 4. 生成完整报告
        System.out.println("\n4. 生成组件报告...");
        String report = ComponentParser.generateReport(components);
        System.out.println(report);

        // 5. 演示单个组件的详细信息显示
        System.out.println("5. 详细组件信息展示:");
        for (Class<?> componentClass : componentClasses) {
            System.out.println("\n--- " + componentClass.getSimpleName() + " 组件详情 ---");
            ComponentScanner.printComponentInfo(componentClass);
        }

        System.out.println("\n=== 演示完成 ===");
    }
}