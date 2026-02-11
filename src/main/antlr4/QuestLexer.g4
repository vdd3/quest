lexer grammar QuestLexer;

@header {
    package cn.easygd.quest.core;
}

// 关键字
FOR: 'for' | 'loop' | '循环';
IF: 'if' | '如果';
ELSE: 'else' | '否则';
WHILE: 'while' | '当';
BREAK: 'break' | '跳出' | '中断';
CONTINUE: 'continue' | '继续';
RETURN: 'return' | '返回';
FUNCTION: 'function' | 'fn' | '函数' | '方法';
MACRO: 'macro' | '宏';
IMPORT: 'import' | '导入' | '引入';
STATIC: 'static' | '静态';
NEW: 'new' | '新建';
BYTE: 'byte' | '字节';
SHORT: 'short' | '短整型';
INT: 'int' | '整数';
LONG: 'long' | '长整数';
FLOAT: 'float' | '浮点数';
DOUBLE: 'double' | '双精度';
CHAR: 'char' | '字符';
BOOL: 'boolean' | '布尔';
NULL: 'null' | '空';
TRUE: 'true' | '是';
FALSE: 'false' | '否';
USE: 'use' | '使用';
KIND: 'kind';
PROCESS: 'process' | '处理';
EXTENDS: 'extends' | '继承';
SUPER: 'super' | '超类';
TRY: 'try' | '捕获';
CATCH: 'catch' | '处理异常';
FINALLY: 'finally' | '必须';
THROW: 'throw' | '抛出';

// kind类型
KIND_SERVICE: 'service';
KIND_AGENT: 'agent';
KIND_ROUTE: 'route';

// unuseful now, but reserve them for future
CLASS: 'class' | '类';
THIS: 'this' | '当前类';

// String Literals

QuoteStringLiteral
    :   '\'' QuoteStringCharacters? '\''
    ;

fragment QuoteStringCharacters
    :   QuoteStringCharacter+
    ;

fragment QuoteStringCharacter
    :   ~['\\]
    |   '\\' '\''?
    ;

fragment ZeroToThree
	:	[0-3]
	;

fragment OctalDigit
	:	[0-7]
	;

fragment HexDigit
	:	[0-9a-fA-F]
	;

// Number Literals

IntegerLiteral
    :   HexIntegerLiteral
    |   OctalIntegerLiteral
    |   BinaryIntegerLiteral
    ;

FloatingPointLiteral
    :   '.' Digits ExponentPart? FloatTypeSuffix?
    |   DecimalNumeral ExponentPart FloatTypeSuffix?
    |   DecimalNumeral FloatTypeSuffix
    ;

IntegerOrFloatingLiteral
    // 1l 1. 1.2 1.2e3 1.2e3f
    :   DecimalNumeral IntegerOrFloating?
    ;

fragment IntegerOrFloating
    :   IntegerTypeSuffix
    |   {
            !(
                ( (_input.LA(2) >= 'a' && _input.LA(2) <= 'z') || (_input.LA(2) >= 'A' && _input.LA(2) <= 'Z') )
                &&
                ( (_input.LA(3) >= 'a' && _input.LA(3) <= 'z') || (_input.LA(3) >= 'A' && _input.LA(3) <= 'Z') )
            )
        }? '.' Digits? ExponentPart? FloatTypeSuffix?
    ;

fragment BinaryIntegerLiteral
    :   BinaryNumeral IntegerTypeSuffix?
    ;

fragment BinaryNumeral
    :   '0' [bB] BinaryDigits
    ;

fragment BinaryDigits
    :   BinaryDigit (BinaryDigitsAndUnderscores? BinaryDigit)?
    ;

fragment BinaryDigit
    :   [01]
    ;

fragment BinaryDigitsAndUnderscores
    :   BinaryDigitOrUnderscore+
    ;

fragment BinaryDigitOrUnderscore
    :   BinaryDigit
    |   '_'
    ;

fragment OctalIntegerLiteral
    :   OctalNumeral IntegerTypeSuffix?
    ;

fragment OctalNumeral
    :   '0' Underscores? OctalDigits
    ;

fragment OctalDigits
    :   OctalDigit (OctalDigitsAndUnderscores? OctalDigit)?
    ;

fragment OctalDigitsAndUnderscores
    :   OctalDigitOrUnderscore+
    ;

fragment OctalDigitOrUnderscore
    :   OctalDigit
    |   '_'
    ;

fragment HexIntegerLiteral
    :   HexNumeral IntegerTypeSuffix?
    ;

fragment HexNumeral
    :   '0' [xX] HexDigits
    ;

fragment HexDigits
    :   HexDigit (HexDigitsAndUnderscores? HexDigit)?
    ;

fragment HexDigitsAndUnderscores
    :   HexDigitOrUnderscore+
    ;

fragment HexDigitOrUnderscore
    :   HexDigit
    |   '_'
    ;

fragment DecimalIntegerLiteral
    :   DecimalNumeral IntegerTypeSuffix?
    ;

fragment IntegerTypeSuffix
    :   [lL]
    ;

fragment DecimalNumeral
    :   '0'
    |   NonZeroDigit (Digits? | Underscores Digits)
    ;

fragment Underscores
    :   '_'+
    ;


fragment NonZeroDigit
	:	[1-9]
	;

fragment Digits
	:	Digit (DigitsAndUnderscores? Digit)?
	;

fragment Digit
	:	'0'
	|	NonZeroDigit
	;

fragment DigitsAndUnderscores
	:	DigitOrUnderscore+
	;

fragment DigitOrUnderscore
    :   Digit
    |   '_'
    ;

fragment HexSignificand
    :   HexNumeral '.'?
    |   '0' [xX] HexDigits? '.' HexDigits
    ;

fragment BinaryExponent
    :   BinaryExponentIndicator SignedInteger
    ;

fragment BinaryExponentIndicator
    :   [pP]
    ;

fragment FloatTypeSuffix
    :   [fFdD]
    ;

fragment ExponentPart
    :   ExponentIndicator SignedInteger
    ;

fragment SignedInteger
    :   Sign? Digits
    ;

fragment Sign
    :   [+-]
    ;

fragment ExponentIndicator
    :   [eE]
    ;

// operator

LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';


// system operator

DOT : '.';
ARROW : '->';
SEMI : ';';
COMMA : ',';
QUESTION : '?';
COLON : ':';
DCOLON: '::';
GT : '>';
LT : '<';
EQ : '=';
NOEQ: '<>';
RIGHSHIFT_ASSGIN: '>>=';
RIGHSHIFT: '>>';
OPTIONAL_CHAINING: '?.';
SPREAD_CHAINING: '*.';
URSHIFT_ASSGIN: '>>>=';
URSHIFT: '>>>';
LSHIFT_ASSGIN: '<<=';
LEFTSHIFT: '<<';
GE: '>=';
LE: '<=';
DOTMUL: '.*';
CARET: '^';
ADD_ASSIGN: '+=';
SUB_ASSIGN: '-=';
AND_ASSIGN: '&=';
OR_ASSIGN: '|=';
MUL_ASSIGN: '*=';
MOD_ASSIGN: '%=';
DIV_ASSIGN: '/=';
XOR_ASSIGN: '^=';

// prefix suffix operator
BANG : '!';
TILDE : '~';

ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
BIT_AND: '&';
BIT_OR: '|';
MOD: '%';

INC: '++';
DEC: '--';

// Whitespace and comments

NEWLINE : '\r' '\n'? | '\n';

WS  :  [ \t\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;

fragment ServiceId
    : [\u0041-\u005A]
    | [\u0061-\u007A]
    ;

fragment IdStart
    // #
    : [\u0023]
    // A-Z
    | [\u0041-\u005A]
    // _
    | [\u005F]
    // @
    | [\u0040]
    // $
    | [\u0024]
    // a-z
    | [\u0061-\u007A]
    ;

fragment IdPart
	: IdStart
	// 、
	| [\u3001]
	// （）
	| [\uFF08-\uFF09]
    // 【】
    | [\u3010-\u3011]
	// 0-9
	| [\u0030-\u0039]
	| [\u007F-\u009F]
	| [\u00AD]
	| [\u0300-\u036F]
	| [\u0483-\u0487]
	| [\u0591-\u05BD]
	| [\u05BF]
	| [\u05C1-\u05C2]
	| [\u05C4-\u05C5]
	| [\u05C7]
	| [\u0600-\u0605]
	| [\u0610-\u061A]
	| [\u061C]
	| [\u064B-\u0669]
	| [\u0670]
	| [\u06D6-\u06DD]
	| [\u06DF-\u06E4]
	| [\u06E7-\u06E8]
	| [\u06EA-\u06ED]
	| [\u06F0-\u06F9]
	| [\u070F]
	| [\u0711]
	| [\u0730-\u074A]
	| [\u07A6-\u07B0]
	| [\u07C0-\u07C9]
	| [\u07EB-\u07F3]
	| [\u0816-\u0819]
	| [\u081B-\u0823]
	| [\u0825-\u0827]
	| [\u0829-\u082D]
	| [\u0859-\u085B]
	| [\u08D4-\u0903]
	| [\u093A-\u093C]
	| [\u093E-\u094F]
	| [\u0951-\u0957]
	| [\u0962-\u0963]
	| [\u0966-\u096F]
	| [\u0981-\u0983]
	| [\u09BC]
	| [\u09BE-\u09C4]
	| [\u09C7-\u09C8]
	| [\u09CB-\u09CD]
	| [\u09D7]
	| [\u09E2-\u09E3]
	| [\u09E6-\u09EF]
	| [\u0A01-\u0A03]
	| [\u0A3C]
	| [\u0A3E-\u0A42]
	| [\u0A47-\u0A48]
	| [\u0A4B-\u0A4D]
	| [\u0A51]
	| [\u0A66-\u0A71]
	| [\u0A75]
	| [\u0A81-\u0A83]
	| [\u0ABC]
	| [\u0ABE-\u0AC5]
	| [\u0AC7-\u0AC9]
	| [\u0ACB-\u0ACD]
	| [\u0AE2-\u0AE3]
	| [\u0AE6-\u0AEF]
	| [\u0AFA-\u0AFF]
	| [\u0B01-\u0B03]
	| [\u0B3C]
	| [\u0B3E-\u0B44]
	| [\u0B47-\u0B48]
	| [\u0B4B-\u0B4D]
	| [\u0B56-\u0B57]
	| [\u0B62-\u0B63]
	| [\u0B66-\u0B6F]
	| [\u0B82]
	| [\u0BBE-\u0BC2]
	| [\u0BC6-\u0BC8]
	| [\u0BCA-\u0BCD]
	| [\u0BD7]
	| [\u0BE6-\u0BEF]
	| [\u0C00-\u0C03]
	| [\u0C3E-\u0C44]
	| [\u0C46-\u0C48]
	| [\u0C4A-\u0C4D]
	| [\u0C55-\u0C56]
	| [\u0C62-\u0C63]
	| [\u0C66-\u0C6F]
	| [\u0C81-\u0C83]
	| [\u0CBC]
	| [\u0CBE-\u0CC4]
	| [\u0CC6-\u0CC8]
	| [\u0CCA-\u0CCD]
	| [\u0CD5-\u0CD6]
	| [\u0CE2-\u0CE3]
	| [\u0CE6-\u0CEF]
	| [\u0D00-\u0D03]
	| [\u0D3B-\u0D3C]
	| [\u0D3E-\u0D44]
	| [\u0D46-\u0D48]
	| [\u0D4A-\u0D4D]
	| [\u0D57]
	| [\u0D62-\u0D63]
	| [\u0D66-\u0D6F]
	| [\u0D82-\u0D83]
	| [\u0DCA]
	| [\u0DCF-\u0DD4]
	| [\u0DD6]
	| [\u0DD8-\u0DDF]
	| [\u0DE6-\u0DEF]
	| [\u0DF2-\u0DF3]
	| [\u0E31]
	| [\u0E34-\u0E3A]
	| [\u0E47-\u0E4E]
	| [\u0E50-\u0E59]
	| [\u0EB1]
	| [\u0EB4-\u0EB9]
	| [\u0EBB-\u0EBC]
	| [\u0EC8-\u0ECD]
	| [\u0ED0-\u0ED9]
	| [\u0F18-\u0F19]
	| [\u0F20-\u0F29]
	| [\u0F35]
	| [\u0F37]
	| [\u0F39]
	| [\u0F3E-\u0F3F]
	| [\u0F71-\u0F84]
	| [\u0F86-\u0F87]
	| [\u0F8D-\u0F97]
	| [\u0F99-\u0FBC]
	| [\u0FC6]
	| [\u102B-\u103E]
	| [\u1040-\u1049]
	| [\u1056-\u1059]
	| [\u105E-\u1060]
	| [\u1062-\u1064]
	| [\u1067-\u106D]
	| [\u1071-\u1074]
	| [\u1082-\u108D]
	| [\u108F-\u109D]
	| [\u135D-\u135F]
	| [\u1712-\u1714]
	| [\u1732-\u1734]
	| [\u1752-\u1753]
	| [\u1772-\u1773]
	| [\u17B4-\u17D3]
	| [\u17DD]
	| [\u17E0-\u17E9]
	| [\u180B-\u180E]
	| [\u1810-\u1819]
	| [\u1885-\u1886]
	| [\u18A9]
	| [\u1920-\u192B]
	| [\u1930-\u193B]
	| [\u1946-\u194F]
	| [\u19D0-\u19D9]
	| [\u1A17-\u1A1B]
	| [\u1A55-\u1A5E]
	| [\u1A60-\u1A7C]
	| [\u1A7F-\u1A89]
	| [\u1A90-\u1A99]
	| [\u1AB0-\u1ABD]
	| [\u1B00-\u1B04]
	| [\u1B34-\u1B44]
	| [\u1B50-\u1B59]
	| [\u1B6B-\u1B73]
	| [\u1B80-\u1B82]
	| [\u1BA1-\u1BAD]
	| [\u1BB0-\u1BB9]
	| [\u1BE6-\u1BF3]
	| [\u1C24-\u1C37]
	| [\u1C40-\u1C49]
	| [\u1C50-\u1C59]
	| [\u1CD0-\u1CD2]
	| [\u1CD4-\u1CE8]
	| [\u1CED]
	| [\u1CF2-\u1CF4]
	| [\u1CF7-\u1CF9]
	| [\u1DC0-\u1DF9]
	| [\u1DFB-\u1DFF]
	| [\u200B-\u200F]
	| [\u202A-\u202E]
	| [\u2060-\u2064]
	| [\u2066-\u206F]
	| [\u20D0-\u20DC]
	| [\u20E1]
	| [\u20E5-\u20F0]
	| [\u2CEF-\u2CF1]
	| [\u2D7F]
	| [\u2DE0-\u2DFF]
	| [\u302A-\u302F]
	| [\u3099-\u309A]
	| [\uA620-\uA629]
	| [\uA66F]
	| [\uA674-\uA67D]
	| [\uA69E-\uA69F]
	| [\uA6F0-\uA6F1]
	| [\uA802]
	| [\uA806]
	| [\uA80B]
	| [\uA823-\uA827]
	| [\uA880-\uA881]
	| [\uA8B4-\uA8C5]
	| [\uA8D0-\uA8D9]
	| [\uA8E0-\uA8F1]
	| [\uA900-\uA909]
	| [\uA926-\uA92D]
	| [\uA947-\uA953]
	| [\uA980-\uA983]
	| [\uA9B3-\uA9C0]
	| [\uA9D0-\uA9D9]
	| [\uA9E5]
	| [\uA9F0-\uA9F9]
	| [\uAA29-\uAA36]
	| [\uAA43]
	| [\uAA4C-\uAA4D]
	| [\uAA50-\uAA59]
	| [\uAA7B-\uAA7D]
	| [\uAAB0]
	| [\uAAB2-\uAAB4]
	| [\uAAB7-\uAAB8]
	| [\uAABE-\uAABF]
	| [\uAAC1]
	| [\uAAEB-\uAAEF]
	| [\uAAF5-\uAAF6]
	| [\uABE3-\uABEA]
	| [\uABEC-\uABED]
	| [\uABF0-\uABF9]
	| [\uFB1E]
	| [\uFE00-\uFE0F]
	| [\uFE20-\uFE2F]
	| [\uFEFF]
	| [\uFF10-\uFF19]
	| [\uFFF9-\uFFFB]
	;

SERVICEID: ServiceId;

// Spring Bean相关标识符
BEAN_ID: [A-Z] [a-z]*;  // 首字母大写的Bean名称
METHOD_ID: [a-z] [a-z]*;  // 小写的方法名

ID
    :   IdStart IdPart*
    ;