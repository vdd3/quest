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
kindDeclaration: AT KIND kindType SEMI;

// kind类型规则
kindType: SERVICE | PRD;

// 业务模块规则
bizModule: AT BUSINESS IDENTIFIER SEMI;

// 模块通用规则
module: (bizModule newlines? serviceModule) | prdModule;

// service类型模块
serviceModule: processModule+ | functionModule;

processModule: PROCESS IDENTIFIER LBRACE newlines? statement* newlines? RBRACE;
functionModule: FUNCTION LBRACE newlines? functionDefinition* newlines? RBRACE;

// prd类型模块
prdModule: requirementModule newlines? descriptionModule newlines? businessModule;

requirementModule: REQUIREMENT LBRACE newlines? textContent* newlines? RBRACE;
descriptionModule: DESCRIPTION LBRACE newlines? textContent* newlines? RBRACE;
businessModule: BUSINESS LBRACE newlines? textContent* newlines? RBRACE;

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
//variableDeclaration: type IDENTIFIER (ASSIGN expression)? SEMI;

// 方法定义规则
functionDefinition: type IDENTIFIER LPAREN parameterList? RPAREN LBRACE newlines? statement* newlines? RBRACE;

// 参数列表规则
parameterList: parameter (COMMA parameter)*;

// 参数规则
parameter: type IDENTIFIER;

// 语句通用规则
statement: type IDENTIFIER (ASSIGN (expression))? SEMI #variableStatement
         | IF LPAREN expression RPAREN block (ELSE block)? #ifStatement
         | FOR LPAREN forControl RPAREN block #forStatement
         | WHILE LPAREN expression RPAREN block #whileStatement
         | expression SEMI #expressionStatement
         | RETURN expression? SEMI #returnStatement;

// 赋值语句规则
//assignmentStatement: expression SEMI;

// 赋值运算符规则
assignmentOperator: ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | MULT_ASSIGN | DIV_ASSIGN | MOD_ASSIGN;

// 表达式语句规则
//expressionStatement: expression SEMI;

// 返回语句规则
//returnStatement: RETURN expression? SEMI;

// 控制流语句规则

// if语句规则
//ifStatement: IF LPAREN expression RPAREN block (ELSE block)?;

// 代码块规则
block: LBRACE newlines? statement* newlines? RBRACE;

// for循环规则
//forStatement: FOR LPAREN forControl RPAREN block;

// for控制规则
forControl: (forVariableDeclaration | expression)? SEMI expression? SEMI expression?;

// while循环规则
//whileStatement: WHILE LPAREN expression RPAREN block;

// Spring Bean调用规则

// use语句规则
//useStatement: (type IDENTIFIER ASSIGN)? USE IDENTIFIER DOT IDENTIFIER LPAREN argumentList? RPAREN SEMI;

// 参数列表规则（用于方法调用）
argumentList: expression (COMMA expression)*;

// 表达式规则
//exprStatement: expression;

// 表达式层级规则
expression: primary #primaryExpr
          | methodInvokeExpression #methodInvokeExpr
          | expression LBRACK expression RBRACK #arrayAccessExpr
          | expression postfix=(INC | DEC) #postfixExpr
          | prefix=(PLUS | MINUS | INC | DEC | NOT) expression #prefixExpr
          | expression binaryOp expression #binaryExpr
          | expression QUESTION expression COLON expression #ternaryExpr
          | LPAREN type RPAREN expression #castExpr
          | IDENTIFIER assignmentOperator expression #assignmentExpr
          ;

// service expression
//serviceExpression: IDENTIFIER DOT IDENTIFIER LPAREN argumentList? RPAREN;

// method invoke expression
methodInvokeExpression: IDENTIFIER LPAREN argumentList? RPAREN #currentMethodInvokeExpr
    | IDENTIFIER DOT IDENTIFIER LPAREN argumentList? RPAREN #classMethodInvokeExpr
//    | USE IDENTIFIER DOT IDENTIFIER LPAREN argumentList? RPAREN #serviceMethodInvokeExpr
    ;

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

newlines: NEWLINE+;