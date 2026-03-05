package cn.easygd.quest.engine.runtime.cli;

import cn.easygd.quest.engine.runtime.module.ServiceModule;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 文件夹解析命令
 * 批量解析文件夹中的 Quest 脚本文件
 *
 * @author Quest Team
 * @since 1.0.0
 */
@Command(
        name = "parse",
        aliases = {"p"},
        description = "解析文件夹中的 Quest 脚本文件",
        mixinStandardHelpOptions = true
)
public class ParseCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "要解析的文件夹路径")
    private File inputDirectory;

    @Option(names = {"-o", "--output"}, description = "输出结果文件路径")
    private File outputFile;

    @Option(names = {"-r", "--recursive"}, description = "递归解析子文件夹")
    private boolean recursive = false;

    @Option(names = {"-f", "--filter"}, description = "文件过滤模式 (默认: *.quest)")
    private String fileFilter = "*.quest";

    @Option(names = {"--format"}, description = "输出格式: json|xml|text")
    private String outputFormat = "text";

    @Option(names = {"--stats"}, description = "显示统计信息")
    private boolean showStats = false;

    @Option(names = {"-v", "--verbose"}, description = "详细输出")
    private boolean verbose = false;

    // 统计信息
    private int totalFiles = 0;
    private int parsedFiles = 0;
    private int errorFiles = 0;
    private List<String> errorMessages = new ArrayList<>();

    /**
     * 执行解析命令
     *
     * @return 退出状态码
     */
    @Override
    public Integer call() {
        try {
            if (verbose) {
                System.out.println("开始解析文件夹: " + inputDirectory.getAbsolutePath());
                System.out.println("递归模式: " + recursive);
                System.out.println("文件过滤: " + fileFilter);
            }

            if (!inputDirectory.exists() || !inputDirectory.isDirectory()) {
                System.err.println("错误: 指定的路径不是有效的文件夹");
                return CommandLine.ExitCode.USAGE;
            }

            // 收集要解析的文件
            List<Path> filesToParse = collectFiles();

            if (filesToParse.isEmpty()) {
                System.out.println("未找到匹配的文件");
                return CommandLine.ExitCode.OK;
            }

            if (verbose) {
                System.out.println("找到 " + filesToParse.size() + " 个文件待解析");
            }

            // 解析所有文件
            List<ParsingResult> results = parseFiles(filesToParse);

            // 输出结果
            outputResults(results);

            // 显示统计信息
            if (showStats) {
                printStatistics();
            }

            return errorFiles > 0 ? CommandLine.ExitCode.SOFTWARE : CommandLine.ExitCode.OK;

        } catch (Exception e) {
            System.err.println("解析过程中发生错误: " + e.getMessage());
            if (verbose) {
                e.printStackTrace();
            }
            return CommandLine.ExitCode.SOFTWARE;
        }
    }

    /**
     * 收集要解析的文件
     */
    private List<Path> collectFiles() throws IOException {
        List<Path> files = new ArrayList<>();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + fileFilter);

        Files.walkFileTree(inputDirectory.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (matcher.matches(file.getFileName())) {
                    files.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                // 如果不是递归模式，跳过子目录
                if (!recursive && !dir.equals(inputDirectory.toPath())) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return files;
    }

    /**
     * 解析文件列表
     */
    private List<ParsingResult> parseFiles(List<Path> files) {
//        FolderParserService parserService = new FolderParserService();
//        FolderParserService.ParseConfig config = new FolderParserService.ParseConfig();
//        config.setSkipErrors(true);
//        config.setMaxThreads(4);
//
//        if (verbose) {
//            config.setProgressCallback(progress -> {
//                System.out.printf("进度: %.1f%% (%d/%d) - %s [%s]%n",
//                        progress.getProgressPercentage(),
//                        progress.getProcessedFiles(),
//                        progress.getTotalFiles(),
//                        progress.getCurrentFile(),
//                        progress.getStatus());
//            });
//        }
//
//        List<FolderParserService.ParseResult> parseResults = parserService.parseFolder(
//                inputDirectory.toPath(), config);
//
//        // 转换为旧的结果格式（为了兼容现有代码）
//        List<ParsingResult> results = new ArrayList<>();
//        totalFiles = parseResults.size();
//
//        for (FolderParserService.ParseResult parseResult : parseResults) {
//            ParsingResult result = new ParsingResult(
//                    parseResult.getFilePath(),
//                    parseResult.isSuccess(),
//                    parseResult.getModule() instanceof ServiceModule ? (ServiceModule) parseResult.getModule() : null,
//                    parseResult.getErrorMessage()
//            );
//            results.add(result);
//
//            if (result.isSuccess()) {
//                parsedFiles++;
//            } else {
//                errorFiles++;
//                errorMessages.add(parseResult.getFilePath().toString() + ": " + parseResult.getErrorMessage());
//            }
//        }
//
//        parserService.shutdown();
        return null;
    }

    /**
     * 解析单个文件
     */
    private ParsingResult parseSingleFile(Path filePath) throws IOException {
        try {
//            // 读取文件内容
//            String content = new String(Files.readAllBytes(filePath));
//
//            // 使用 ANTLR 解析
//            QuestParser parser = new QuestParser(content);
//            QuestServiceVisitor visitor = new QuestServiceVisitor();
//
//            // 访问解析树
//            parser.script().accept(visitor);
//
//            ServiceModule module = visitor.getModule();

            return new ParsingResult(filePath, true, null, null);

        } catch (Exception e) {
            return new ParsingResult(filePath, false, null, e.getMessage());
        }
    }

    /**
     * 输出解析结果
     */
    private void outputResults(List<ParsingResult> results) throws IOException {
        StringBuilder output = new StringBuilder();

        switch (outputFormat.toLowerCase()) {
            case "json":
                output.append(formatAsJson(results));
                break;
            case "xml":
                output.append(formatAsXml(results));
                break;
            case "text":
            default:
                output.append(formatAsText(results));
                break;
        }

        // 输出到控制台
        System.out.println(output.toString());

        // 如果指定了输出文件，写入文件
        if (outputFile != null) {
            Files.createDirectories(outputFile.toPath().getParent());
            Files.write(outputFile.toPath(), output.toString().getBytes());
            if (verbose) {
                System.out.println("结果已保存到: " + outputFile.getAbsolutePath());
            }
        }
    }

    /**
     * 格式化为文本输出
     */
    private String formatAsText(List<ParsingResult> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Quest 脚本解析结果 ===\n\n");

        for (ParsingResult result : results) {
            sb.append("文件: ").append(result.getFilePath()).append("\n");
            if (result.isSuccess()) {
                sb.append("状态: ✓ 解析成功\n");
                ServiceModule module = result.getModule();
                if (module != null) {
                    sb.append("业务模块: ").append(module.getBizModule() != null ? module.getBizModule() : "未定义").append("\n");
                    sb.append("流程数量: ").append(module.getProcessStatementMap().size()).append("\n");
                    sb.append("函数数量: ").append(module.getFunctionStatementMap().size()).append("\n");
                }
            } else {
                sb.append("状态: ✗ 解析失败\n");
                sb.append("错误: ").append(result.getErrorMessage()).append("\n");
            }
            sb.append("---\n");
        }

        return sb.toString();
    }

    /**
     * 格式化为 JSON 输出
     */
    private String formatAsJson(List<ParsingResult> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"results\": [\n");

        for (int i = 0; i < results.size(); i++) {
            ParsingResult result = results.get(i);
            sb.append("    {\n");
            sb.append("      \"file\": \"").append(result.getFilePath().toString().replace("\\", "\\\\")).append("\",\n");
            sb.append("      \"success\": ").append(result.isSuccess()).append(",\n");

            if (result.isSuccess() && result.getModule() != null) {
                ServiceModule module = result.getModule();
                sb.append("      \"bizModule\": \"").append(module.getBizModule() != null ? module.getBizModule() : "").append("\",\n");
                sb.append("      \"processCount\": ").append(module.getProcessStatementMap().size()).append(",\n");
                sb.append("      \"functionCount\": ").append(module.getFunctionStatementMap().size()).append("\n");
            } else {
                sb.append("      \"error\": \"").append(result.getErrorMessage() != null ? result.getErrorMessage().replace("\"", "\\\"") : "").append("\"\n");
            }

            sb.append("    }");
            if (i < results.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("  ]\n}");
        return sb.toString();
    }

    /**
     * 格式化为 XML 输出
     */
    private String formatAsXml(List<ParsingResult> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<quest-results>\n");

        for (ParsingResult result : results) {
            sb.append("  <result>\n");
            sb.append("    <file>").append(result.getFilePath().toString()).append("</file>\n");
            sb.append("    <success>").append(result.isSuccess()).append("</success>\n");

            if (result.isSuccess() && result.getModule() != null) {
                ServiceModule module = result.getModule();
                sb.append("    <biz-module>").append(module.getBizModule() != null ? module.getBizModule() : "").append("</biz-module>\n");
                sb.append("    <process-count>").append(module.getProcessStatementMap().size()).append("</process-count>\n");
                sb.append("    <function-count>").append(module.getFunctionStatementMap().size()).append("</function-count>\n");
            } else {
                sb.append("    <error>").append(result.getErrorMessage() != null ? result.getErrorMessage() : "").append("</error>\n");
            }

            sb.append("  </result>\n");
        }

        sb.append("</quest-results>");
        return sb.toString();
    }

    /**
     * 打印统计信息
     */
    private void printStatistics() {
        System.out.println("\n=== 解析统计 ===");
        System.out.println("总文件数: " + totalFiles);
        System.out.println("成功解析: " + parsedFiles);
        System.out.println("解析失败: " + errorFiles);
        System.out.println("成功率: " + String.format("%.2f%%", (parsedFiles * 100.0 / totalFiles)));

        if (!errorMessages.isEmpty()) {
            System.out.println("\n错误详情:");
            errorMessages.forEach(System.err::println);
        }
    }

    /**
     * 解析结果内部类
     */
    private static class ParsingResult {
        private final Path filePath;
        private final boolean success;
        private final ServiceModule module;
        private final String errorMessage;

        public ParsingResult(Path filePath, boolean success, ServiceModule module, String errorMessage) {
            this.filePath = filePath;
            this.success = success;
            this.module = module;
            this.errorMessage = errorMessage;
        }

        public Path getFilePath() {
            return filePath;
        }

        public boolean isSuccess() {
            return success;
        }

        public ServiceModule getModule() {
            return module;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}