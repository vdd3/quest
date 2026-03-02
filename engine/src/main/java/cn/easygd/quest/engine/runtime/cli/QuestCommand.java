package cn.easygd.quest.engine.runtime.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Quest 脚本引擎主命令类
 * 提供基于 picocli 的命令行界面
 *
 * @author Quest Team
 * @since 1.0.0
 */
@Command(
        name = "quest",
        version = "Quest CLI 1.0",
        description = "Quest 脚本引擎命令行工具",
        mixinStandardHelpOptions = true,
        subcommands = {
                ParseCommand.class,
                ScannerCommand.class
        }
)
public class QuestCommand implements Callable<Integer> {

    @Option(names = {"-v", "--verbose"}, description = "启用详细输出模式")
    private boolean verbose = false;

    @Option(names = {"--config"}, description = "配置文件路径")
    private File configFile;

    @Parameters(description = "输入文件或目录路径")
    private File inputFile;

    /**
     * 主命令执行方法
     *
     * @return 退出状态码
     */
    @Override
    public Integer call() {
        if (verbose) {
            System.out.println("Quest Script Engine 启动...");
            System.out.println("输入文件: " + (inputFile != null ? inputFile.getAbsolutePath() : "未指定"));
            System.out.println("配置文件: " + (configFile != null ? configFile.getAbsolutePath() : "使用默认配置"));
        }

        // 如果没有指定子命令，显示帮助信息
        CommandLine.usage(this, System.out);
        return CommandLine.ExitCode.USAGE;
    }

    /**
     * 获取详细模式状态
     *
     * @return 是否启用详细模式
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * 获取配置文件
     *
     * @return 配置文件对象
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * 获取输入文件
     *
     * @return 输入文件对象
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * 主程序入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new QuestCommand()).execute(args);
        System.exit(exitCode);
    }
}