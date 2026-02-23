parser grammar QuestParser;

options {
    tokenVocab = QuestLexer;
}

@header {
    package cn.easygd.quest.core;
}

// 脚本顶层规则
script: kindDeclaration module EOF;

// kind声明规则
kindDeclaration: AT KIND kindType SEMI;

// kind类型规则
kindType: SERVICE | PRD | ENTITY;

// 业务模块规则
bizModule: AT BUSINESS inputTxt SEMI;

// 模块通用规则
module: (bizModule newlines? serviceModule) | prdModule | entityModule;

// service类型模块
serviceModule: processModule+ functionModule?;

processModule: PROCESS inputTxt LBRACE newlines? statement* newlines? RBRACE;
functionModule: FUNCTION LBRACE newlines? functionDefinition* newlines? RBRACE;

// prd类型模块
prdModule: requirementModule newlines? descriptionModule newlines? businessModule;

requirementModule: REQUIREMENT LBRACE newlines? textContent* newlines? RBRACE;
descriptionModule: DESCRIPTION LBRACE newlines? textContent* newlines? RBRACE;
businessModule: BUSINESS LBRACE newlines? textContent* newlines? RBRACE;

// 文本内容规则（用于prd模块）
textContent: inputTxt | CHINESE_CHAR | STRING | INTEGER | FLOAT_LITERAL;

// entity类型模块
entityModule: (classDeclaration | interfaceDeclaration | enumDeclaration)*;

classDeclaration: usageLevel ABSTRACT? CLASS IDENTIFIER (EXTENDS classType)? (IMPLEMENTS classType (COMMA classType)*)? LBRACE newlines? classBody* newlines? RBRACE;

classBody: usageLevel STATIC? FINAL? type IDENTIFIER (ASSIGN expression)? SEMI
    | (AT OVERRIDE newlines)? usageLevel type IDENTIFIER LPAREN parameterList? RPAREN LBRACE newlines? statement* newlines? RBRACE
    ;

interfaceDeclaration: INTERFACE IDENTIFIER LBRACE newlines? interfaceBody* newlines? RBRACE;

interfaceBody: type IDENTIFIER ((LPAREN parameterList RPAREN) | (LPAREN RPAREN))? SEMI
    | DEFAULT type IDENTIFIER LPAREN parameterList? RPAREN LBRACE newlines? statement* newlines? RBRACE
    ;

enumDeclaration: ENUM IDENTIFIER LBRACE newlines? enumBody* newlines? RBRACE;

enumBody: IDENTIFIER (ASSIGN INTEGER)? (COMMA IDENTIFIER (ASSIGN INTEGER)?)*;

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

// 方法定义规则
functionDefinition: type inputTxt LPAREN parameterList? RPAREN LBRACE newlines? statement* newlines? RBRACE;

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

// 赋值运算符规则
assignmentOperator: ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | MULT_ASSIGN | DIV_ASSIGN | MOD_ASSIGN;

// 代码块规则
block: LBRACE newlines? statement* newlines? RBRACE;

// for控制规则
forControl: (forVariableDeclaration | expression)? SEMI expression? SEMI expression?;

// 参数列表规则（用于方法调用）
argumentList: expression (COMMA expression)*;

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
          | NEW classType LPAREN argumentList? RPAREN #newExpr
          ;

// method invoke expression
methodInvokeExpression: IDENTIFIER LPAREN argumentList? RPAREN #currentMethodInvokeExpr
    | IDENTIFIER DOT IDENTIFIER LPAREN argumentList? RPAREN #classMethodInvokeExpr
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

usageLevel: PRIVATE | PUBLIC | PROTECTED;

inputTxt: IDENTIFIER
    | BACKQUOTE IDENTIFIER BACKQUOTE
    ;