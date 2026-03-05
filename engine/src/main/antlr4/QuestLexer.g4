lexer grammar QuestLexer;

@header {
    package cn.easygd.quest.engine.core;
}

// Whitespace handling
WS: [ \t\r\n]+ -> skip;

// Comment handling
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
DOC_COMMENT: '/**' .*? '*/' -> skip;

// Newline handling
NEWLINE : '\r' '\n'? | '\n';

// Keyword definition
KIND: 'kind'
    | '类型' { setText("kind");}
    ;
SERVICE: 'service'
    | '服务' { setText("service");}
    ;
PRD: 'prd'
    | '产品' { setText("prd");}
    ;
PROCESS: 'process'
    | '逻辑' { setText("process");}
    ;
FUNCTION: 'function'
    | '函数' { setText("function");}
    ;
ENTITY: 'entity'
    | '实体' { setText("entity");}
    ;

// prd module keywords
REQUIREMENT: 'requirement'
    | '需求' { setText("requirement");}
    ;
DESCRIPTION: 'description'
    | '描述' { setText("description");}
    ;
BUSINESS: 'business'
    | '业务' { setText("business");}
    ;
NOTE: 'note'
    | '注释' { setText("note");}
    ;

// Java control flow keywords
IF: 'if'
    | 'alt' { setText("if");}
    |'如果' { setText("if");}
    ;
ELSE: 'else'
    | '否则' { setText("else");}
    ;
FOR: 'for'
    | 'loop' { setText("for");}
    |'循环' { setText("for");}
    ;
WHILE: 'while'
    | '当' { setText("while");}
    ;
RETURN: 'return'
    | '返回' { setText("return");}
    ;

// Java basic data types
BYTE: 'byte';
SHORT: 'short';
INT: 'int'
    | 'Integer'
    ;
LONG: 'long'
    | 'Long'
    ;
FLOAT: 'float';
DOUBLE: 'double';
CHAR: 'char';
BOOLEAN: 'boolean'
    | 'Boolean'
    ;
STRING_TYPE: 'String';
VOID: 'void'
    | 'Void'
    ;

// Java inheritance and generics keywords
EXTENDS: 'extends'
    | '继承' { setText("extends");}
    ;
SUPER: 'super'
    | '父类' { setText("super");}
    ;
CLASS: 'class'
    | '类' { setText("class");}
    ;
INTERFACE: 'interface'
    | '接口' { setText("interface");}
    ;
ENUM: 'enum'
    | '枚举' { setText("enum");}
    ;
PUBLIC: 'public'
    | '共有' { setText("public");}
    ;
PROTECTED: 'protected'
    | '受保护的' { setText("protected");}
    ;
PRIVATE: 'private'
    | '私有' { setText("private");}
    ;
STATIC: 'static'
    | '静态' { setText("static");}
    ;
ABSTRACT: 'abstract'
    | '抽象' { setText("abstract");}
    ;
DEFAULT: 'default'
    | '默认' { setText("default");}
    ;
FINAL: 'final'
    | '不可变' { setText("final");}
    ;
IMPLEMENTS: 'implements'
    | '实现' { setText("implements");}
    ;
OVERRIDE: 'override'
    | '重写' { setText("override");}
    ;
NEW: 'new'
    | '新建' { setText("new");}
    ;
THIS: 'this'
    | '本类' { setText("this");}
    ;

// Special value literals
NULL: 'null'
    | '空' { setText("null");}
    ;
TRUE: 'true'
    | '是' { setText("true");}
    ;
FALSE: 'false'
    | '否' { setText("false");}
    ;

// Arithmetic operators
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';

// Comparison operators
EQ: '==';
NEQ: '!=';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';

// Logical operators
AND: '&&';
OR: '||';
NOT: '!';

// Assignment operators
ASSIGN: '=';
PLUS_ASSIGN: '+=';
MINUS_ASSIGN: '-=';
MULT_ASSIGN: '*=';
DIV_ASSIGN: '/=';
MOD_ASSIGN: '%=';

// Increment and decrement operators
INC: '++';
DEC: '--';

// Delimiters
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACK: '[';
RBRACK: ']';
SEMI: ';';
COLON: ':';
COMMA: ',';
DOT: '.';
QUESTION: '?';
AT: '@';
BACKQUOTE: '`';

// Chinese character support
CHINESE_CHAR: [\u4e00-\u9fff];

// Identifier rules (support Chinese and English)
IDENTIFIER: [A-Za-z_][A-Za-z0-9_]* | CHINESE_CHAR+;

// Literals
INTEGER: [0-9]+;
FLOAT_LITERAL: [0-9]+ ('.' [0-9]+)? ([eE] [+-]? [0-9]+)?;
STRING: '"' (ESC | ~["\\])* '"';

fragment ESC: '\\' (["\\/bfnrt] | UNICODE);
fragment UNICODE: 'u' HEX HEX HEX HEX;
fragment HEX: [0-9a-fA-F];

// fix keyword clash
TXT: BACKQUOTE -> pushMode(Txt_Mode);

mode Txt_Mode;

// keywords
THIS_IDENTIFIER: IDENTIFIER -> type(IDENTIFIER);
THIS_WS: WS -> type(WS);
THIS_BUSINESS: BUSINESS -> type(IDENTIFIER);
THIS_REQUIREMENT: REQUIREMENT -> type(IDENTIFIER);
THIS_DESCRIPTION: DESCRIPTION -> type(IDENTIFIER);
THIS_KIND: KIND -> type(IDENTIFIER);
THIS_SERVICE: SERVICE -> type(IDENTIFIER);
THIS_PRD: PRD -> type(IDENTIFIER);
THIS_PROCESS: PROCESS -> type(IDENTIFIER);
THIS_FUNCTION: FUNCTION -> type(IDENTIFIER);
THIS_ENTITY: ENTITY -> type(IDENTIFIER);
THIS_IF: IF -> type(IDENTIFIER);
THIS_ELSE: ELSE -> type(IDENTIFIER);
THIS_FOR: FOR -> type(IDENTIFIER);
THIS_WHILE: WHILE -> type(IDENTIFIER);
THIS_RETURN: RETURN -> type(IDENTIFIER);
THIS_VOID: VOID -> type(IDENTIFIER);
THIS_BOOLEAN: BOOLEAN -> type(IDENTIFIER);
THIS_STRING_TYPE: STRING_TYPE -> type(IDENTIFIER);
THIS_CHAR: CHAR -> type(IDENTIFIER);
THIS_INT: INT -> type(IDENTIFIER);
THIS_LONG: LONG -> type(IDENTIFIER);
THIS_DOUBLE: DOUBLE -> type(IDENTIFIER);
THIS_FLOAT: FLOAT -> type(IDENTIFIER);
THIS_BYTE: BYTE -> type(IDENTIFIER);
THIS_SHORT: SHORT -> type(IDENTIFIER);
THIS_SUPER: SUPER -> type(IDENTIFIER);
THIS_CLASS: CLASS -> type(IDENTIFIER);
THIS_INTERFACE: INTERFACE -> type(IDENTIFIER);
THIS_ENUM: ENUM -> type(IDENTIFIER);
THIS_PUBLIC: PUBLIC -> type(IDENTIFIER);
THIS_PROTECTED: PROTECTED -> type(IDENTIFIER);
THIS_PRIVATE: PRIVATE -> type(IDENTIFIER);
THIS_STATIC: STATIC -> type(IDENTIFIER);
THIS_ABSTRACT: ABSTRACT -> type(IDENTIFIER);
THIS_DEFAULT: DEFAULT -> type(IDENTIFIER);
THIS_FINAL: FINAL -> type(IDENTIFIER);
THIS_IMPLEMENTS: IMPLEMENTS -> type(IDENTIFIER);
THIS_OVERRIDE: OVERRIDE -> type(IDENTIFIER);
THIS_NEW: NEW -> type(IDENTIFIER);
THIS_NULL: NULL -> type(IDENTIFIER);
THIS_TRUE: TRUE -> type(IDENTIFIER);
THIS_FALSE: FALSE -> type(IDENTIFIER);
THIS_PLUS: PLUS -> type(IDENTIFIER);
THIS_MINUS: MINUS -> type(IDENTIFIER);
THIS_MULT: MULT -> type(IDENTIFIER);
THIS_DIV: DIV -> type(IDENTIFIER);
THIS_MOD: MOD -> type(IDENTIFIER);
THIS_EQ: EQ -> type(IDENTIFIER);
THIS_NEQ: NEQ -> type(IDENTIFIER);
THIS_LT: LT -> type(IDENTIFIER);

// quit this mode
SEMI_QUIT: SEMI -> popMode;
