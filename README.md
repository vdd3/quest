# Quest代码生成器使用指南

## 简介

Quest是一个开源的代码生成框架，允许用户通过编写脚本来自动生成Java代码块。该项目可以作为依赖库被其他项目引入使用。

## 功能特性

1. **自动注解扫描** - 自动扫描项目中带有`@AgentComment`注解的类和`@AgentMethod`注解的方法
2. **脚本解析** - 使用ANTLR解析自定义的Quest脚本语言
3. **代码生成** - 将脚本转换为可执行的Java代码块
4. **Markdown导出** - 将生成的代码集中导出到Markdown文档中

## 快速开始

### 1. 添加依赖

在您的项目`pom.xml`中添加Quest依赖：

```xml
<dependency>
    <groupId>cn.easygd</groupId>
    <artifactId>quest</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 2. 标记您的类和方法

```java
import cn.easygd.quest.runtime.annotation.AgentComment;
import cn.easygd.quest.runtime.annotation.AgentMethod;

@AgentComment
public class UserService {
    
    @AgentMethod
    public String getUserById(Long id) {
        return "User-" + id;
    }
    
    @AgentMethod
    public boolean updateUser(String name, Integer age) {
        // 实现逻辑
        return true;
    }
}
```

### 3. 编写Quest脚本

创建`.quest`文件：

```quest
kind: agent

// 用户处理脚本
int userId = 123
string userName = "张三"
int userAge = 25

if (userId > 0) {
    string result = getUserById(userId)
    println("查询到用户: " + result)
    
    boolean success = updateUser(userName, userAge)
    if (success) {
        println("用户信息更新成功")
    }
}
```

### 4. 运行代码生成器

```java
QuestRunner runner = new QuestRunner();
Map<String, String> generatedCodes = runner.run(
    "com.yourcompany.project",  // 目标包名
    "./scripts",                // 脚本目录
    "./output"                  // 输出目录
);
```

或者使用命令行：

```bash
java -jar quest-runner.jar com.yourcompany.project ./scripts ./output
```

## API文档

### 主要类

#### QuestRunner
主运行类，整合所有功能。

```java
public class QuestRunner {
    // 主要运行方法
    public Map<String, String> run(String targetPackage, 
                                 List<File> scriptFiles, 
                                 String outputDir)
    
    // 简化运行方法
    public Map<String, String> run(String targetPackage, 
                                 String scriptDir, 
                                 String outputDir)
}
```

#### AnnotationScanner
注解扫描器，负责扫描项目中的注解。

#### ScriptEngine
脚本引擎，负责解析Quest脚本。

#### CodeGenerator
代码生成器，将解析树转换为Java代码。

#### MarkdownExporter
Markdown导出器，将生成的代码导出为Markdown文档。

## 脚本语法

Quest脚本支持以下语法特性：

### 基本类型
- `int`, `long`, `float`, `double`, `boolean`, `string`

### 控制结构
```quest
// 条件语句
if (condition) {
    // 代码块
} else {
    // 代码块
}

// 循环语句
for (int i = 0; i < 10; i++) {
    // 循环体
}

while (condition) {
    // 循环体
}
```

### 变量声明
```quest
int x = 10
string name = "test"
boolean flag = true
```

### 数组和集合
```quest
int[] numbers = [1, 2, 3, 4, 5]
map<string, int> userScores = { "张三": 95, "李四": 87 }
```

## 输出格式

生成的Markdown文件包含：

1. **文档头部** - 包含生成时间和统计信息
2. **目录** - 快速导航到各个代码块
3. **代码块章节** - 每个目标类对应一个章节
4. **类信息表格** - 显示类的基本信息
5. **方法信息表格** - 显示可用的AgentMethod方法
6. **生成的Java代码** - 实际的代码块
7. **使用说明** - 如何使用生成的代码

## 最佳实践

1. **合理使用注解** - 只在需要代码生成的类上使用`@AgentComment`
2. **方法命名规范** - `@AgentMethod`方法应该有清晰的业务含义
3. **脚本组织** - 将相关的脚本放在同一目录下
4. **版本控制** - 将生成的代码纳入版本控制系统
5. **测试验证** - 对生成的代码进行充分测试

## 故障排除

### 常见问题

1. **找不到注解类**
   - 确保包名正确
   - 检查类路径配置

2. **脚本语法错误**
   - 使用`ScriptEngine.validateSyntax()`验证语法
   - 检查Quest语法规范

3. **代码生成失败**
   - 查看详细日志信息
   - 确保目标类和方法可访问

### 日志配置

Quest使用SLF4J，可以通过配置文件控制日志级别：

```properties
# logback.xml 或 log4j.properties
logger.cn.easygd.quest=DEBUG
```

## 贡献指南

欢迎提交Issue和Pull Request！

1. Fork项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建Pull Request

## 许可证

MIT License

## 联系方式

如有问题，请联系项目维护者或提交GitHub Issue。