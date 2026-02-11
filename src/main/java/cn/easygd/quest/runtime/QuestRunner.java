package cn.easygd.quest.runtime;

import cn.easygd.quest.runtime.engine.ScriptEngine;
import cn.easygd.quest.runtime.exporter.MarkdownExporter;
import cn.easygd.quest.runtime.generator.CodeGenerator;
import cn.easygd.quest.runtime.model.AnnotationInfo;
import cn.easygd.quest.runtime.scanner.AnnotationScanner;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Quest运行器主类
 * 整合所有功能模块，提供统一的运行入口
 */
public class QuestRunner {
    private static final Logger logger = LoggerFactory.getLogger(QuestRunner.class);
    
    private final AnnotationScanner annotationScanner;
    private final ScriptEngine scriptEngine;
    private final CodeGenerator codeGenerator;
    private final MarkdownExporter markdownExporter;
    
    public QuestRunner() {
        this.annotationScanner = new AnnotationScanner();
        this.scriptEngine = new ScriptEngine();
        this.codeGenerator = new CodeGenerator();
        this.markdownExporter = new MarkdownExporter();
    }

    /**
     * 主要运行方法
     * @param targetPackage 目标包名
     * @param scriptFiles 脚本文件列表
     * @param outputDir 输出目录
     * @return 生成的代码映射
     */
    public Map<String, String> run(String targetPackage, 
                                 List<File> scriptFiles, 
                                 String outputDir) {
        
        System.out.println("=== Quest代码生成器启动 ===");
        System.out.println("目标包: " + targetPackage);
        System.out.println("脚本文件数: " + scriptFiles.size());
        System.out.println("输出目录: " + outputDir);
        
        try {
            // 1. 扫描注解
            List<AnnotationInfo> annotationInfos = annotationScanner.scanPackage(targetPackage);
            if (annotationInfos.isEmpty()) {
                System.out.println("警告: 未找到任何带@AgentComment注解的类");
                return Collections.emptyMap();
            }
            System.out.println("发现 " + annotationInfos.size() + " 个带@AgentComment注解的类");
            
            // 2. 处理脚本文件
            Map<String, String> generatedCodes = new ConcurrentHashMap<>();
            
            for (File scriptFile : scriptFiles) {
                if (!scriptFile.exists()) {
                    System.out.println("警告: 脚本文件不存在: " + scriptFile.getAbsolutePath());
                    continue;
                }
                
                System.out.println("处理脚本文件: " + scriptFile.getName());
                
                // 读取脚本内容
                String scriptContent = readFileContent(scriptFile);
                if (scriptContent == null || scriptContent.trim().isEmpty()) {
                    System.out.println("警告: 脚本文件为空: " + scriptFile.getName());
                    continue;
                }
                
                // 格式化脚本
                scriptContent = scriptEngine.formatScript(scriptContent);
                
                // 验证语法
                if (!scriptEngine.validateSyntax(scriptContent)) {
                    System.out.println("错误: 脚本语法错误: " + scriptFile.getName());
                    continue;
                }
                
                // 解析脚本
                ParseTree parseTree = scriptEngine.parseScript(scriptContent);
                
                // 为每个注解类生成代码
                for (AnnotationInfo annotationInfo : annotationInfos) {
                    try {
                        String generatedCode = codeGenerator.generateCode(parseTree, annotationInfo);
                        String key = annotationInfo.getClassName() + "#" + scriptFile.getName();
                        generatedCodes.put(key, generatedCode);
                        System.out.println("为类 " + annotationInfo.getClassName() + " 生成代码成功");
                    } catch (Exception e) {
                        System.out.println("为类 " + annotationInfo.getClassName() + " 生成代码时出错: " + e.getMessage());
                    }
                }
            }
            
            // 3. 导出到Markdown
            if (!generatedCodes.isEmpty()) {
                String outputPath = markdownExporter.getDefaultOutputPath(outputDir);
                markdownExporter.exportToMarkdown(generatedCodes, outputPath, annotationInfos);
            }
            
            System.out.println("=== Quest代码生成完成 ===");
            System.out.println("总共生成 " + generatedCodes.size() + " 个代码块");
            
            return generatedCodes;
            
        } catch (Exception e) {
            System.out.println("运行过程中发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Quest运行失败: " + e.getMessage(), e);
        }
    }

    /**
     * 简化的运行方法（使用默认设置）
     * @param targetPackage 目标包名
     * @param scriptDir 脚本目录
     * @param outputDir 输出目录
     * @return 生成的代码映射
     */
    public Map<String, String> run(String targetPackage, String scriptDir, String outputDir) {
        // 收集脚本文件
        List<File> scriptFiles = collectScriptFiles(scriptDir);
        return run(targetPackage, scriptFiles, outputDir);
    }

    /**
     * 收集脚本文件
     */
    private List<File> collectScriptFiles(String scriptDir) {
        List<File> scriptFiles = new ArrayList<>();
        
        File dir = new File(scriptDir);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.warn("脚本目录不存在或不是目录: {}", scriptDir);
            return scriptFiles;
        }
        
        File[] files = dir.listFiles((d, name) -> name.endsWith(".quest") || name.endsWith(".ql"));
        if (files != null) {
            scriptFiles.addAll(Arrays.asList(files));
            logger.info("在目录 {} 中找到 {} 个脚本文件", scriptDir, files.length);
        }
        
        return scriptFiles;
    }

    /**
     * 读取文件内容
     */
    private String readFileContent(File file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            logger.error("读取文件失败: {}", file.getAbsolutePath(), e);
            return null;
        }
    }

    /**
     * 获取扫描器实例（用于测试或其他用途）
     */
    public AnnotationScanner getAnnotationScanner() {
        return annotationScanner;
    }

    /**
     * 获取脚本引擎实例
     */
    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    /**
     * 获取代码生成器实例
     */
    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    /**
     * 获取Markdown导出器实例
     */
    public MarkdownExporter getMarkdownExporter() {
        return markdownExporter;
    }

    /**
     * 主方法 - 命令行入口
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("使用方法: java -jar quest-runner.jar <目标包名> <脚本目录> <输出目录>");
            System.out.println("示例: java -jar quest-runner.jar com.example.project ./scripts ./output");
            return;
        }
        
        String targetPackage = args[0];
        String scriptDir = args[1];
        String outputDir = args[2];
        
        try {
            QuestRunner runner = new QuestRunner();
            Map<String, String> results = runner.run(targetPackage, scriptDir, outputDir);
            
            System.out.println("代码生成完成！");
            System.out.println("生成的代码块数量: " + results.size());
            
        } catch (Exception e) {
            System.err.println("运行失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}