package cn.easygd.quest.runtime.scanner;

import cn.easygd.quest.runtime.annotation.AgentComment;
import cn.easygd.quest.runtime.annotation.AgentMethod;
import cn.easygd.quest.runtime.model.AnnotationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 注解扫描器
 * 扫描指定包路径下的所有类，找出带有@AgentComment注解的类和其中的@AgentMethod方法
 */
public class AnnotationScanner {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationScanner.class);
    
    private final Set<String> scannedPackages = new HashSet<>();
    private final List<AnnotationInfo> annotationInfos = new ArrayList<>();

    /**
     * 扫描指定包及其子包中的所有类
     * @param basePackage 基础包名
     * @return 注解信息列表
     */
    public List<AnnotationInfo> scanPackage(String basePackage) {
        if (scannedPackages.contains(basePackage)) {
            logger.warn("包 {} 已经扫描过，跳过重复扫描", basePackage);
            return annotationInfos;
        }
        
        scannedPackages.add(basePackage);
        logger.info("开始扫描包: {}", basePackage);
        
        try {
            // 获取类加载器
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            
            // 将包名转换为路径格式
            String path = basePackage.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();
                
                if ("file".equals(protocol)) {
                    // 处理文件系统中的类
                    scanFileSystem(resource.getFile(), basePackage);
                } else if ("jar".equals(protocol)) {
                    // 处理JAR包中的类
                    scanJarFile(resource, basePackage);
                }
            }
            
        } catch (IOException e) {
            logger.error("扫描包 {} 时发生错误", basePackage, e);
        }
        
        logger.info("扫描完成，找到 {} 个带@AgentComment注解的类", annotationInfos.size());
        return annotationInfos;
    }

    /**
     * 扫描文件系统中的类文件
     */
    private void scanFileSystem(String filePath, String basePackage) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            return;
        }
        
        File[] files = directory.listFiles();
        if (files == null) return;
        
        for (File file : files) {
            if (file.isDirectory()) {
                // 递归扫描子目录
                String subPackage = basePackage + "." + file.getName();
                scanFileSystem(file.getAbsolutePath(), subPackage);
            } else if (file.getName().endsWith(".class")) {
                // 处理.class文件
                String className = file.getName().substring(0, file.getName().length() - 6);
                String fullClassName = basePackage + "." + className;
                processClass(fullClassName);
            }
        }
    }

    /**
     * 扫描JAR包中的类文件
     */
    private void scanJarFile(URL resource, String basePackage) {
        try {
            String jarPath = resource.getPath();
            if (jarPath.startsWith("file:")) {
                jarPath = jarPath.substring(5, jarPath.indexOf("!"));
            }
            
            JarFile jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = jarFile.entries();
            
            String packagePath = basePackage.replace('.', '/') + "/";
            
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                
                if (entryName.startsWith(packagePath) && entryName.endsWith(".class")) {
                    String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                    processClass(className);
                }
            }
            
            jarFile.close();
        } catch (IOException e) {
            logger.error("扫描JAR文件时发生错误", e);
        }
    }

    /**
     * 处理单个类
     */
    private void processClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            
            // 检查类是否有@AgentComment注解
            if (clazz.isAnnotationPresent(AgentComment.class)) {
                AnnotationInfo info = createAnnotationInfo(clazz);
                
                // 查找类中带有@AgentMethod注解的方法
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(AgentMethod.class)) {
                        AnnotationInfo.MethodInfo methodInfo = new AnnotationInfo.MethodInfo(
                            method.getName(), method
                        );
                        info.addAgentMethod(methodInfo);
                    }
                }
                
                annotationInfos.add(info);
                logger.debug("发现带@AgentComment注解的类: {}", className);
                
                if (!info.getAgentMethods().isEmpty()) {
                    logger.debug("该类包含 {} 个@AgentMethod方法", info.getAgentMethods().size());
                }
            }
            
        } catch (ClassNotFoundException e) {
            logger.warn("无法加载类: {}", className);
        } catch (Exception e) {
            logger.error("处理类 {} 时发生错误", className, e);
        }
    }

    /**
     * 创建注解信息对象
     */
    private AnnotationInfo createAnnotationInfo(Class<?> clazz) {
        return new AnnotationInfo(
            clazz.getName(),
            clazz.getSimpleName(),
            clazz.getPackage().getName(),
            clazz
        );
    }

    /**
     * 清除扫描缓存
     */
    public void clearCache() {
        scannedPackages.clear();
        annotationInfos.clear();
        logger.info("已清除扫描缓存");
    }

    /**
     * 获取扫描结果
     */
    public List<AnnotationInfo> getAnnotationInfos() {
        return new ArrayList<>(annotationInfos);
    }

    /**
     * 获取扫描过的包
     */
    public Set<String> getScannedPackages() {
        return new HashSet<>(scannedPackages);
    }
}