parser grammar QuestParser;

@header {
    package cn.easygd.quest.core;
    import static cn.easygd.quest.core.KindType.*;
}

options {
    tokenVocab = QuestLexer;
}

progeam
    : (newlines? importDeclaration)* (newlines? kindStatement) newlines? statement? EOF
    ;

statement
    : routeStatement
    | serviceStatement
    ;

// 路由模块
routeStatement
    : varId ARROW varId (varId ARROW varId)*
    ;

serviceStatement
    : processStatement+
    ;

// 逻辑模块
processStatement
    : PROCESS LBRACE (newlines? commonStatement)* newlines? RBRACE
    ;

// 通用模块
commonStatement
    : declType variableDeclaratorList
    ;

// 脚本类型模块
kindStatement
    : KIND varId SEMI
    ;

newlines : NEWLINE+;

// decl type
declType
    :   primitiveType dims?
    |   clsType dims?
    ;

primitiveType
    :   'byte'
    |   'short'
    |   'int'
    |   'long'
    |   'float'
    |   'double'
    |   'boolean'
    |   'char'
    ;

clsType
    :   varId ('.' varId)* typeArguments?
    ;

variableDeclaratorList
    :   variableDeclarator (newlines? ',' newlines? variableDeclarator)*
    ;

// TODO
variableDeclarator
    :  USE SERVICEID newlines? '.' newlines? SERVICEID ('(' (varId(',' varId)*)? ')')
    ;

variableDeclaratorId
    :   varId dims?
    ;

typeArguments
    :   LT newlines? typeArgumentList? newlines? (GT | RIGHSHIFT | URSHIFT)?
    |   NOEQ
    ;

typeArgumentList
    :   typeArgument (newlines? ',' newlines? typeArgument)*
    ;

typeArgument
    :   referenceType
    |   wildcard
    ;

referenceType
    :   clsType dims?
    |   primitiveType dims
    ;

wildcard
    :   '?' wildcardBounds?
    ;

wildcardBounds
    :   'extends' referenceType
    |   'super' referenceType
    ;

dims
    :   LBRACK RBRACK (LBRACK RBRACK)*
    ;

importDeclaration
    //  import xxx
    :   IMPORT varId ('.' varId)* ';' # importCls
    // import .*
    |   IMPORT varId ('.' varId)* (DOT MUL | DOTMUL) ';' # importPack
    ;

varId
    : ID
    | FUNCTION
    ;