package cn.easygd.quest.engine.runtime.cli;

import com.google.common.collect.Lists;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
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
        description = "Quest CLI",
        mixinStandardHelpOptions = true,
        subcommands = {
                ParseCommand.class,
                ScannerCommand.class,
        }
)
public class QuestCommand implements Callable<Integer> {

    /**
     * 退出命令，用于展示提示信息，实际消费根据用户输入的指令来决定
     */
    @Option(names = {"-exit", "-quit"}, description = "退出")
    private boolean exit = false;

    /**
     * 退出命令列表
     */
    private static final List<String> EXIT_COMMANDS = Lists.newArrayList("-exit", "-quit");

    /**
     * 主命令执行方法
     *
     * @return 退出状态码
     */
    @Override
    public Integer call() {

        // init
        CommandLine commandLine = new CommandLine(this);
//        commandLine.addSubcommand(new ParseCommand());
//        commandLine.addSubcommand(new ScannerCommand());

        String input;

        System.out.println("欢迎");

        while (true) {
            // 获取用户输入
            Console console = System.console();
            if (console == null) {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine().trim();
            } else {
                input = console.readLine();
            }

            // 检测输入
            if (input == null) {
                // 用户按下了 Ctrl+D (Linux/Mac) 或 Ctrl+Z (Windows)
                break;
            }

            // 检查退出命令
            if (EXIT_COMMANDS.contains(input)) {
                break;
            }

            try {
                input = input.trim();
                if (input.isEmpty()) {
                    // 1. 创建一个字节数组输出流
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    PrintStream printStream = new PrintStream(outStream);

                    // 2. 将帮助信息“打印”到这个流中
                    commandLine.usage(printStream);

                    // 3. 转换为字符串
                    String helpText = outStream.toString(StandardCharsets.UTF_8.name());
                    System.out.println(helpText);
                    continue;
                }

                // 5. 使用 picocli 解析并执行输入的命令
                // parseWithHandlers 允许我们更精细地控制执行流程，但 execute 对于简单场景也足够
                // 这里我们将输入字符串分割成数组传给 picocli
                String[] args = input.split("\\s+");

                commandLine.execute(args);
            } catch (Exception ex) {
                // 处理未捕获的异常
                ex.printStackTrace();
            }
        }

        System.out.println("再见！");
        return 0;
    }

    /**
     * 主程序入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        int execute = new CommandLine(new QuestCommand()).execute();
        System.exit(execute);
    }
}