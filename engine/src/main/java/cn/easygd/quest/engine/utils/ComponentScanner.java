package cn.easygd.quest.engine.utils;

import cn.easygd.quest.api.annotation.QuestComponent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Quest组件扫描器
 * 用于扫描项目中带有@QuestComponent注解的类和方法
 *
 * @author VD
 */
public class ComponentScanner {

    /**
     * 扫描指定包路径下的所有带@QuestComponent注解的类
     *
     * @param packageName 包名
     * @return 带注解的类列表
     */
    public static List<Class<?>> scanComponents(String packageName) {
        List<Class<?>> components = new ArrayList<>();

        try {
            // 获取类加载器
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            // 将包名转换为路径
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();

                if ("file".equals(protocol)) {
                    // 处理文件系统中的类
                    scanFileSystem(resource.getFile(), packageName, components);
                } else if ("jar".equals(protocol)) {
                    // 处理JAR包中的类
                    scanJarFile(resource, packageName, components);
                }
            }
        } catch (IOException e) {
            System.err.println("扫描组件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }

        return components;
    }

    /**
     * 扫描文件系统中的类文件
     */
    private static void scanFileSystem(String path, String packageName, List<Class<?>> components) {
        File directory = new File(path);
        if (!directory.exists()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归扫描子目录
                    scanFileSystem(file.getAbsolutePath(),
                            packageName + "." + file.getName(), components);
                } else if (file.getName().endsWith(".class")) {
                    // 处理class文件
                    String className = packageName + "." +
                            file.getName().substring(0, file.getName().length() - 6);
                    checkAndAddComponent(className, components);
                }
            }
        }
    }

    /**
     * 扫描JAR包中的类文件
     */
    private static void scanJarFile(URL url, String packageName, List<Class<?>> components) {
        try {
            String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
            JarFile jarFile = new JarFile(jarPath);

            Enumeration<JarEntry> entries = jarFile.entries();
            String packagePath = packageName.replace('.', '/') + "/";

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.startsWith(packagePath) && entryName.endsWith(".class")) {
                    String className = entryName
                            .substring(0, entryName.length() - 6)
                            .replace('/', '.');
                    checkAndAddComponent(className, components);
                }
            }

            jarFile.close();
        } catch (IOException e) {
            System.err.println("扫描JAR文件时发生错误: " + e.getMessage());
        }
    }

    /**
     * 检查类是否带有@QuestComponent注解并添加到列表
     */
    private static void checkAndAddComponent(String className, List<Class<?>> components) {
        try {
            Class<?> clazz = Class.forName(className);

            // 检查类级别的注解
            if (clazz.isAnnotationPresent(QuestComponent.class)) {
                components.add(clazz);
                System.out.println("发现带@QuestComponent注解的类: " + className);
            }

            // 检查方法级别的注解
            java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.isAnnotationPresent(QuestComponent.class)) {
                    components.add(clazz);
                    System.out.println("发现带@QuestComponent注解的方法: " +
                            className + "#" + method.getName());
                    break; // 一个类只需要添加一次
                }
            }
        } catch (ClassNotFoundException e) {
            // 忽略无法加载的类
        } catch (NoClassDefFoundError e) {
            // 忽略依赖缺失的类
        }
    }

    /**
     * 获取组件的详细信息
     */
    public static void printComponentInfo(Class<?> componentClass) {
        QuestComponent annotation = componentClass.getAnnotation(QuestComponent.class);
        if (annotation != null) {
            System.out.println("组件信息:");
            System.out.println("  类名: " + componentClass.getName());
            System.out.println("  别名: " + annotation.alis());
            System.out.println("  描述: " + annotation.description());

            // 显示带注解的方法
            java.lang.reflect.Method[] methods = componentClass.getDeclaredMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.isAnnotationPresent(QuestComponent.class)) {
                    QuestComponent methodAnnotation = method.getAnnotation(QuestComponent.class);
                    System.out.println("  方法: " + method.getName());
                    System.out.println("    别名: " + methodAnnotation.alis());
                    System.out.println("    描述: " + methodAnnotation.description());
                }
            }
            System.out.println();
        }
    }

    /**
     * 主方法 - 示例用法
     */
    public static void main(String[] args) {
        // 扫描示例包
        String packageName = "cn.easygd.quest"; // 根据实际情况修改

        System.out.println("开始扫描包: " + packageName);
        List<Class<?>> components = scanComponents(packageName);

        System.out.println("\n找到 " + components.size() + " 个带@QuestComponent注解的组件:");
        for (Class<?> component : components) {
            printComponentInfo(component);
        }
    }
}