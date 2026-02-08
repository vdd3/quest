parser grammar QuestParser;

@header {
    package cn.easygd.quest.core;
    import static cn.easygd.quest.core.KindType.*;
}

options {
    tokenVocab = QuestLexer;
}

progeam
    : (newlines? kindStatement) newlines? statement? EOF
    ;

statement
    : routeStatement
    | serviceStatement
    ;

// 路由语句: A -> B C -> D
routeStatement
    : varId ARROW varId (varId ARROW varId)*
    ;

serviceStatement
    : PROCESS LBRACE newlines? processStatement newlines? RBRACE
    ;

// 逻辑模块
processStatement
    :
    ;

// 脚本类型模块
kindStatement
    : KIND varId SEMI
    ;

newlines : NEWLINE+;

varId
    : ID
    | FUNCTION
    ;