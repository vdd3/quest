package cn.easygd.quest.engine.runtime.enums;

/**
 * @author VD
 */
public enum StatementType {

    /*=====================kind module======================*/

    PROCESS,

    FUNCTION,

    /*=====================stack module======================*/

    VARIABLE,

    IF,

    FOR,

    WHILE,

    EXPRESSION,

    TOKEN,

    TYPE,

    RETURN,

    BLOCK,

    TXT,

    /*=====================expression======================*/

    PRIMARY_EXPR,

    ASSIGNMENT_EXPR,

    BINARY_EXPR,

    POSTFIX_EXPR,

    PREFIX_EXPR,

    TERNARY_EXPR,

    CAST_EXPR,

    ARRAY_ACCESS_EXPR,

    CLASS_METHOD_INVOKE_EXPR,

    FUNCTION_INVOKE_EXPR,

    ;

}
