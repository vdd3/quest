parser grammar QuestParser;

options {
    tokenVocab = QuestLexer;
}

@header {
    package cn.easygd.quest.core;
}

// 脚本顶层规则
script: kindDeclaration module* EOF;

// kind声明规则
kindDeclaration: AT KIND kindType;

// kind类型规则
kindType: SERVICE | PRD;

// 模块通用规则
module: serviceModule | prdModule;

// service类型模块
serviceModule: processModule | functionModule;

processModule: PROCESS LBRACE statement* RBRACE;
functionModule: FUNCTION LBRACE functionDefinition* RBRACE;

// prd类型模块
prdModule: requirementModule | descriptionModule | businessModule;

requirementModule: REQUIREMENT LBRACE textContent* RBRACE;
descriptionModule: DESCRIPTION LBRACE textContent* RBRACE;
businessModule: BUSINESS LBRACE textContent* RBRACE;

// 文本内容规则（用于prd模块）
textContent: IDENTIFIER | CHINESE_CHAR | STRING | INTEGER | FLOAT_LITERAL;

// 类型系统规则
type: primitiveType | classType;

// 基本数据类型规则
primitiveType: BYTE | SHORT | INT | LONG | FLOAT | DOUBLE | CHAR | BOOLEAN | STRING_TYPE;

// 类类型规则（支持泛型）
classType: IDENTIFIER (typeArguments)? (DOT IDENTIFIER (typeArguments)?)*;

// 类型参数规则
typeArguments: LT typeArgument (COMMA typeArgument)* GT;

// 类型参数规则（支持extends和super）
typeArgument: type
            | QUESTION (EXTENDS type)?
            | QUESTION SUPER type;

// 变量声明规则（用于for循环，不包含分号）
forVariableDeclaration: type IDENTIFIER (ASSIGN expression)?;

// 变量声明规则（普通变量声明，包含分号）
variableDeclaration: type IDENTIFIER (ASSIGN expression)? SEMI;

// 方法定义规则
functionDefinition: type IDENTIFIER LPAREN parameterList? RPAREN LBRACE statement* RBRACE;

// 参数列表规则
parameterList: parameter (COMMA parameter)*;

// 参数规则
parameter: type IDENTIFIER;

// 语句通用规则
statement: variableDeclaration
//         | useAssignmentStatement
         | assignmentStatement
         | ifStatement
         | forStatement
         | whileStatement
         | useStatement
         | expressionStatement
         | returnStatement;

// use赋值语句规则（支持 User user = use ... 格式）
useAssignmentStatement: type IDENTIFIER ASSIGN USE IDENTIFIER DOT IDENTIFIER LPAREN argumentList? RPAREN SEMI;

// 赋值语句规则
assignmentStatement: expression SEMI;

// 赋值运算符规则
assignmentOperator: ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | MULT_ASSIGN | DIV_ASSIGN | MOD_ASSIGN;

// 表达式语句规则
expressionStatement: expression SEMI;

// 返回语句规则
returnStatement: RETURN expression? SEMI;

// 控制流语句规则

// if语句规则
ifStatement: IF LPAREN expression RPAREN block (ELSE block)?;

// 代码块规则
block: LBRACE statement* RBRACE;

// for循环规则
forStatement: FOR LPAREN forControl RPAREN block;

// for控制规则
forControl: (forVariableDeclaration | expression)? SEMI expression? SEMI expression?;

// while循环规则
whileStatement: WHILE LPAREN expression RPAREN block;

// Spring Bean调用规则

// use语句规则
useStatement: (type IDENTIFIER ASSIGN)? USE IDENTIFIER DOT IDENTIFIER LPAREN argumentList? RPAREN SEMI;

// 参数列表规则（用于方法调用）
argumentList: expression (COMMA expression)*;

// 表达式规则

// 表达式层级规则
expression: primary
          | expression DOT IDENTIFIER
          | expression LPAREN argumentList? RPAREN
          | expression LBRACK expression RBRACK
          | expression postfix=(INC | DEC)
          | prefix=(PLUS | MINUS | INC | DEC | NOT) expression
          | expression binaryOp expression
          | expression QUESTION expression COLON expression
          | LPAREN type RPAREN expression
          | IDENTIFIER assignmentOperator expression;

// 基本表达式规则
primary: literal
       | IDENTIFIER
       | SUPER (DOT IDENTIFIER)?
       | LPAREN expression RPAREN;

// 字面量规则
literal: INTEGER
       | FLOAT_LITERAL
       | STRING
       | TRUE
       | FALSE
       | NULL;

// 二元运算符规则
binaryOp: MULT | DIV | MOD
        | PLUS | MINUS
        | LT | GT | LE | GE | EQ | NEQ
        | AND | OR;