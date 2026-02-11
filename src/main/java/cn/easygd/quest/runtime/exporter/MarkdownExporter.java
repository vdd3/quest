package cn.easygd.quest.runtime.exporter;

import cn.easygd.quest.runtime.model.AnnotationInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Markdown导出器
 * 将生成的Java代码块导出到Markdown文件中
 */
public class MarkdownExporter {
    private static final Logger logger = LoggerFactory.getLogger(MarkdownExporter.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 导出代码到Markdown文件
     * @param generatedCodes 生成的代码映射（类名 -> 代码内容）
     * @param outputPath 输出文件路径
     * @param annotationInfos 注解信息列表
     * @throws IOException 当文件操作失败时抛出
     */
    public void exportToMarkdown(Map<String, String> generatedCodes, 
                               String outputPath, 
                               List<AnnotationInfo> annotationInfos) throws IOException {
        
        logger.info("开始导出代码到Markdown文件: {}", outputPath);
        
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        
        // 创建父目录（如果不存在）
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            logger.debug("创建输出目录: {}", parentDir.getAbsolutePath());
        }
        
        // 构建Markdown内容
        StringBuilder markdownContent = new StringBuilder();
        
        // 添加文档头部
        appendDocumentHeader(markdownContent, annotationInfos.size());
        
        // 添加目录
        appendTableOfContents(markdownContent, generatedCodes);
        
        // 添加各个代码块
        int index = 1;
        for (Map.Entry<String, String> entry : generatedCodes.entrySet()) {
            String className = entry.getKey();
            String codeContent = entry.getValue();
            
            // 查找对应的注解信息
            AnnotationInfo annotationInfo = findAnnotationInfo(className, annotationInfos);
            
            appendCodeSection(markdownContent, index++, className, codeContent, annotationInfo);
        }
        
        // 添加文档尾部
        appendDocumentFooter(markdownContent);
        
        // 写入文件
        FileUtils.write(outputFile, markdownContent.toString(), StandardCharsets.UTF_8);
        
        logger.info("Markdown文件导出完成，共 {} 个代码块", generatedCodes.size());
        logger.info("输出文件位置: {}", outputFile.getAbsolutePath());
    }

    /**
     * 添加文档头部
     */
    private void appendDocumentHeader(StringBuilder content, int codeBlockCount) {
        content.append("# Quest代码生成报告\n\n");
        content.append("> 自动生成于: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("\n\n");
        content.append("**说明**: 本文档包含通过Quest脚本自动生成的Java代码块。\n\n");
        content.append("- 总计生成代码块数量: ").append(codeBlockCount).append("\n");
        content.append("- 生成时间: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("\n\n");
        content.append("---\n\n");
    }

    /**
     * 添加目录
     */
    private void appendTableOfContents(StringBuilder content, Map<String, String> generatedCodes) {
        content.append("## 目录\n\n");
        
        int index = 1;
        for (String className : generatedCodes.keySet()) {
            String displayName = extractSimpleClassName(className);
            content.append(index++).append(". [").append(displayName).append("](#")
                   .append(getAnchorName(className)).append(")\n");
        }
        
        content.append("\n---\n\n");
    }

    /**
     * 添加代码章节
     */
    private void appendCodeSection(StringBuilder content, int index, String className, 
                                 String codeContent, AnnotationInfo annotationInfo) {
        String simpleClassName = extractSimpleClassName(className);
        String anchorName = getAnchorName(className);
        
        // 章节标题
        content.append("## ").append(index).append(". ").append(simpleClassName)
               .append(" {#").append(anchorName).append("}\n\n");
        
        // 类信息
        appendClassInfo(content, annotationInfo);
        
        // 方法信息
        if (annotationInfo != null && !annotationInfo.getAgentMethods().isEmpty()) {
            appendMethodInfo(content, annotationInfo);
        }
        
        // 代码块
        content.append("### 生成的Java代码\n\n");
        content.append("```java\n");
        content.append(codeContent);
        content.append("\n```\n\n");
        
        // 使用说明
        appendUsageInstructions(content, className, annotationInfo);
        
        content.append("---\n\n");
    }

    /**
     * 添加类信息
     */
    private void appendClassInfo(StringBuilder content, AnnotationInfo annotationInfo) {
        if (annotationInfo == null) return;
        
        content.append("### 类信息\n\n");
        content.append("| 属性 | 值 |\n");
        content.append("|------|-----|\n");
        content.append("| 完整类名 | `").append(annotationInfo.getClassName()).append("` |\n");
        content.append("| 简单类名 | `").append(annotationInfo.getClassSimpleName()).append("` |\n");
        content.append("| 包名 | `").append(annotationInfo.getPackageName()).append("` |\n");
        content.append("| @AgentMethod方法数 | ").append(annotationInfo.getAgentMethods().size()).append(" |\n\n");
    }

    /**
     * 添加方法信息
     */
    private void appendMethodInfo(StringBuilder content, AnnotationInfo annotationInfo) {
        content.append("### 可用方法\n\n");
        content.append("| 方法名 | 返回类型 | 参数 |\n");
        content.append("|--------|----------|------|\n");
        
        for (AnnotationInfo.MethodInfo method : annotationInfo.getAgentMethods()) {
            StringBuilder params = new StringBuilder();
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) params.append(", ");
                params.append(paramTypes[i].getSimpleName()).append(" param").append(i + 1);
            }
            
            content.append("| `").append(method.getMethodName()).append("` | ")
                   .append("`").append(method.getReturnType().getSimpleName()).append("` | ")
                   .append("`").append(params.toString()).append("` |\n");
        }
        content.append("\n");
    }

    /**
     * 添加使用说明
     */
    private void appendUsageInstructions(StringBuilder content, String className, 
                                       AnnotationInfo annotationInfo) {
        content.append("### 使用说明\n\n");
        content.append("1. 将上述代码复制到您的Java项目中\n");
        content.append("2. 确保目标类 `").append(className).append("` 在类路径中可用\n");
        
        if (annotationInfo != null && !annotationInfo.getAgentMethods().isEmpty()) {
            content.append("3. 可以调用以下方法:\n");
            for (AnnotationInfo.MethodInfo method : annotationInfo.getAgentMethods()) {
                content.append("   - `").append(method.getMethodName()).append("()`\n");
            }
        }
        
        content.append("4. 根据实际需求调整变量名和逻辑\n\n");
    }

    /**
     * 添加文档尾部
     */
    private void appendDocumentFooter(StringBuilder content) {
        content.append("## 文档信息\n\n");
        content.append("**生成工具**: Quest代码生成器\n\n");
        content.append("**生成时间**: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("\n\n");
        content.append("**注意事项**:\n");
        content.append("- 请仔细检查生成的代码是否符合您的业务需求\n");
        content.append("- 建议在生产环境中使用前进行充分测试\n");
        content.append("- 如有问题，请参考Quest官方文档或联系技术支持\n\n");
    }

    /**
     * 提取简单类名
     */
    private String extractSimpleClassName(String fullClassName) {
        int lastDotIndex = fullClassName.lastIndexOf('.');
        return lastDotIndex >= 0 ? fullClassName.substring(lastDotIndex + 1) : fullClassName;
    }

    /**
     * 生成锚点名称
     */
    private String getAnchorName(String className) {
        return className.toLowerCase().replaceAll("[^a-zA-Z0-9]", "-");
    }

    /**
     * 查找注解信息
     */
    private AnnotationInfo findAnnotationInfo(String className, List<AnnotationInfo> annotationInfos) {
        return annotationInfos.stream()
                .filter(info -> info.getClassName().equals(className))
                .findFirst()
                .orElse(null);
    }

    /**
     * 设置输出目录
     * @param baseDir 基础目录
     * @return 完整的输出文件路径
     */
    public String getDefaultOutputPath(String baseDir) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return baseDir + File.separator + "quest-generated-code-" + timestamp + ".md";
    }
}