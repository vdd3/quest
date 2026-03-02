package cn.easygd.quest.engine.runtime.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * @author VD
 */
@Command(
        name = "scanner",
        aliases = {"sc"},
        description = "查找项目中引入了QuestComponent的组件",
        mixinStandardHelpOptions = true
)
public class ScannerCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "要解析的文件夹路径")
    private File inputDirectory;

    @Override
    public Integer call() throws Exception {


        return 0;
    }
}
