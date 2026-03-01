parser grammar QuestParser;

options {
    tokenVocab = QuestLexer;
}

@header {
    package cn.easygd.quest.engine.core;
}

// Script top-level rule
script: kindDeclaration module EOF;

// kind declaration rule
kindDeclaration: AT KIND kindType SEMI;

// kind type rule
kindType: SERVICE | PRD | ENTITY;

// Business module rule
bizModule: AT BUSINESS inputTxt SEMI;

// Module general rule
module: (bizModule newlines? serviceModule) | prdModule | entityModule;

// service type module
serviceModule: processModule+ functionModule?;

processModule: PROCESS inputTxt LBRACE newlines? statement* newlines? RBRACE;
functionModule: FUNCTION LBRACE newlines? functionDefinition* newlines? RBRACE;

// prd type module
prdModule: requirementModule newlines? descriptionModule newlines? businessModule;

requirementModule: REQUIREMENT LBRACE newlines? textContent* newlines? RBRACE;
descriptionModule: DESCRIPTION LBRACE newlines? textContent* newlines? RBRACE;
businessModule: BUSINESS LBRACE newlines? textContent* newlines? RBRACE;

// Text content rule (for prd module)
textContent: inputTxt | CHINESE_CHAR | STRING | INTEGER | FLOAT_LITERAL;

// entity type module
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

// Type system rule
type: primitiveType | classType;

// Basic data type rule
primitiveType: BYTE | SHORT | INT | LONG | FLOAT | DOUBLE | CHAR | BOOLEAN | STRING_TYPE | VOID;

// Class type rule (supports generics)
classType: IDENTIFIER (typeArguments)? (DOT IDENTIFIER (typeArguments)?)*;

// Type parameter rule
typeArguments: LT typeArgument (COMMA typeArgument)* GT;

// Type parameter rule (supports extends and super)
typeArgument: type
            | QUESTION (EXTENDS type)?
            | QUESTION SUPER type;

// Variable declaration rule (for for loop, without semicolon)
forVariableDeclaration: type IDENTIFIER (ASSIGN expression)?;

// Method definition rule
functionDefinition: type inputTxt LPAREN parameterList? RPAREN LBRACE newlines? statement* newlines? RBRACE;

// Parameter list rule
parameterList: parameter (COMMA parameter)*;

// Parameter rule
parameter: type IDENTIFIER;

// Statement general rule
statement: type IDENTIFIER (ASSIGN (expression))? SEMI #variableStatement
         | IF LPAREN expression RPAREN block (ELSE block)? #ifStatement
         | FOR LPAREN forControl RPAREN block #forStatement
         | WHILE LPAREN expression RPAREN block #whileStatement
         | expression SEMI #expressionStatement
         | NOTE COLON inputTxt #noteStatement
         | RETURN expression? SEMI #returnStatement;

// Assignment operator rule
assignmentOperator: ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | MULT_ASSIGN | DIV_ASSIGN | MOD_ASSIGN;

// Code block rule
block: LBRACE newlines? statement* newlines? RBRACE;

// for control rule
forControl: (forVariableDeclaration | expression)? SEMI expression? SEMI expression?;

// Argument list rule (for method calls)
argumentList: expression (COMMA expression)*;

// Expression hierarchy rule
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

// Primary expression rule
primary: literal
       | IDENTIFIER
       | SUPER (DOT IDENTIFIER)?
       | LPAREN expression RPAREN;

// Literal rule
literal: INTEGER
       | FLOAT_LITERAL
       | STRING
       | TRUE
       | FALSE
       | NULL;

// Binary operator rule
binaryOp: MULT | DIV | MOD
        | PLUS | MINUS
        | LT | GT | LE | GE | EQ | NEQ
        | AND | OR;

newlines: NEWLINE+;

usageLevel: PRIVATE | PUBLIC | PROTECTED;

inputTxt: IDENTIFIER
    | BACKQUOTE IDENTIFIER BACKQUOTE
    ;