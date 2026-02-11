parser grammar QuestParser;

@header {
    package cn.easygd.quest.core;
}

options {
    tokenVocab = QuestLexer;
}

program
    : kindStament newlines? blockStatements? EOF
    ;

kindStament
    : KIND ':' kindList
    ;

kindList
    : KIND_SERVICE
    | KIND_AGENT
    | KIND_ROUTE
    ;

blockStatements
    :   blockStatement+
    ;

newlines : NEWLINE+;

nextStatement
    : {_input.LA(1) == Token.EOF || _input.LA(1) == QuestLexer.RBRACE}? | ';' | NEWLINE?;

blockStatement
    :   localVariableDeclaration ';' # localVariableDeclarationStatement
    |   THROW expression nextStatement # throwStatement
    |   WHILE '(' newlines? expression newlines? ')' '{' newlines? blockStatements? newlines? '}' # whileStatement
    |   FOR '(' newlines? forInit (forCondition=expression)? ';' newlines? (forUpdate=expression)? newlines? ')' '{' newlines? blockStatements? newlines? '}' # traditionalForStatement
    |   FOR '(' newlines? declType? varId ':' expression newlines? ')' '{' newlines? blockStatements? newlines? '}' # forEachStatement
    |   FUNCTION varId '(' newlines? formalOrInferredParameterList? newlines? ')' LBRACE newlines? blockStatements? newlines? RBRACE # functionStatement
    |   MACRO varId LBRACE newlines? blockStatements? newlines? RBRACE # macroStatement
    |   (BREAK | CONTINUE) nextStatement # breakContinueStatement
    |   RETURN expression? nextStatement # returnStatement
    |   (';' | NEWLINE) # emptyStatement
    |   expression nextStatement # expressionStatement
    ;

localVariableDeclaration
    :   declType variableDeclaratorList
    ;

forInit
    : localVariableDeclaration ';'
    | expression ';'
    | ';'
    ;

variableDeclaratorList
    : variableDeclarator (newlines? ',' newlines? variableDeclarator)*
    | USE SERVICEID newlines? '.' newlines? SERVICEID ('(' (varId(',' varId)*)? ')')
    ;

variableDeclarator
    :   variableDeclaratorId (EQ newlines? variableInitializer)?
    ;

variableDeclaratorId
    :   varId dims?
    ;

variableInitializer
    :   expression
    |   arrayInitializer
    ;

arrayInitializer
    :   LBRACE newlines? variableInitializerList? newlines? RBRACE
    ;

variableInitializerList
    :   variableInitializer (newlines? ',' newlines? variableInitializer)* ','?
    ;

// decl type
declType
    :   primitiveType dims?
    |   clsType dims?
    ;

declTypeNoArr
    : primitiveType
    | clsType
    ;

primitiveType
    :   BYTE
    |   SHORT
    |   INT
    |   LONG
    |   FLOAT
    |   DOUBLE
    |   BOOL
    |   CHAR
    ;

referenceType
    :   clsType dims?
    |   primitiveType dims
    ;

dims
    :   LBRACK RBRACK (LBRACK RBRACK)*
    ;

clsTypeNoTypeArguments
    :   varId ('.' varId)*
    ;

clsType
    :   varId ('.' varId)* typeArguments?
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

wildcard
    :   '?' wildcardBounds?
    ;

wildcardBounds
    :   EXTENDS referenceType
    |   SUPER referenceType
    ;

// expression
expression
    :   leftHandSide assignOperator newlines? expression
    ;

leftHandSide
    :   varId (LPAREN newlines? argumentList? newlines? RPAREN)? (newlines? pathPart)*
    ;

primaryNoFixPathable
    :   literal # constExpr
    |   '(' newlines? expression newlines? ')' # groupExpr
    |   NEW varId ('.' varId)* typeArguments? '(' newlines? argumentList? newlines? ')' # newObjExpr
    |   NEW declTypeNoArr dimExprs # newEmptyArrExpr
    |   NEW declTypeNoArr dims arrayInitializer # newInitArrExpr
    |   varId (LPAREN newlines? argumentList? newlines? RPAREN)? # varIdExpr
    |   primitiveType # typeExpr
    |   '[' newlines? listItems? newlines? ']' # listExpr
    |   LBRACE newlines? mapEntries newlines? RBRACE # mapExpr
    |   LBRACE newlines? blockStatements? newlines? RBRACE # blockExpr
    ;

primaryNoFixNonPathable
    :   qlIf # ifExpr
    |   TRY LBRACE newlines? blockStatements? newlines? RBRACE tryCatches? (newlines? tryFinally)? # tryCatchExpr
    |   lambdaParameters ARROW newlines? ( LBRACE newlines? blockStatements? newlines? RBRACE | expression) # lambdaExpr
    ;

qlIf : IF '(' newlines? condition=expression newlines? ')' newlines? THEN? newlines? thenBody (newlines? ELSE newlines? elseBody)?;

thenBody
    : LBRACE newlines? blockStatements? newlines? RBRACE
    | nonExpressionStatement
    | expression
    ;

elseBody
    : LBRACE newlines? blockStatements? newlines? RBRACE
    | qlIf
    | nonExpressionStatement
    | expression
    ;

// Non-expression statements that can appear in if-then-else bodies
// This excludes 'expression nextStatement' to avoid ambiguity
nonExpressionStatement
    : localVariableDeclaration ';'
    | THROW expression nextStatement
    | WHILE '(' newlines? expression newlines? ')' '{' newlines? blockStatements? newlines? '}'
    | FOR '(' newlines? forInit (forCondition=expression)? ';' newlines? (forUpdate=expression)? newlines? ')' '{' newlines? blockStatements? newlines? '}'
    | FOR '(' newlines? declType? varId ':' expression newlines? ')' '{' newlines? blockStatements? newlines? '}'
    | FUNCTION varId '(' newlines? formalOrInferredParameterList? newlines? ')' LBRACE newlines? blockStatements? newlines? RBRACE
    | MACRO varId LBRACE newlines? blockStatements? newlines? RBRACE
    | (BREAK | CONTINUE) nextStatement
    | RETURN expression? nextStatement
    | (';' | NEWLINE)
    ;

listItems
    : expression (newlines? ',' newlines? expression)* ','?
    ;

dimExprs
    :   ('[' newlines? expression newlines? ']')+
    ;

tryCatches
    : tryCatch (newlines? tryCatch)*
    ;

tryCatch
    : CATCH '(' catchParams ')' LBRACE newlines? blockStatements? newlines? RBRACE
    ;

catchParams
    : (declType (BIT_OR declType)*)? varId
    ;

tryFinally
    : FINALLY LBRACE newlines? blockStatements? newlines? RBRACE
    ;

mapEntries
    : ':'
    | mapEntry (',' newlines? mapEntry)* ','?
    ;

mapEntry
    : mapKey newlines? ':' newlines? mapValue
    ;

mapValue
    : {_input.LT(-2).getText().equals("'@class'")}? QuoteStringLiteral # clsValue
    | expression # eValue
    ;

mapKey
    : idMapKey # idKey
    | QuoteStringLiteral # quoteStringKey
    ;

idMapKey
    :   varId
    |   FOR
    |   IF
    |   ELSE
    |   WHILE
    |   BREAK
    |   CONTINUE
    |   RETURN
    |   FUNCTION
    |   MACRO
    |   IMPORT
    |   STATIC
    |   NEW
    |   BYTE
    |   SHORT
    |   INT
    |   LONG
    |   FLOAT
    |   DOUBLE
    |   CHAR
    |   BOOL
    |   NULL
    |   TRUE
    |   FALSE
    |   EXTENDS
    |   SUPER
    |   TRY
    |   CATCH
    |   FINALLY
    |   THROW
    |   CLASS
    |   THIS
    ;

pathPart
    :   '.' varId '(' newlines? argumentList? newlines? ')' # methodInvoke
    |   OPTIONAL_CHAINING varId '(' newlines? argumentList? newlines? ')' # optionalMethodInvoke
    |   SPREAD_CHAINING varId '(' newlines? argumentList? newlines? ')' # spreadMethodInvoke
    |   '.' fieldId # fieldAccess
    |   OPTIONAL_CHAINING fieldId # optionalFieldAccess
    |   SPREAD_CHAINING fieldId # spreadFieldAccess
    |   DCOLON varId # methodAccess
    |   '[' newlines? indexValueExpr? newlines? ']' # indexExpr
    ;

fieldId
    :   varId
    |   CLASS
    |   QuoteStringLiteral
    ;

indexValueExpr
    :   expression # singleIndex
    |   start=expression? newlines? ':' newlines? end=expression? # sliceIndex
    ;

argumentList
    :   expression (newlines? ',' newlines? expression)*
    ;

literal
    :   IntegerLiteral
    |   FloatingPointLiteral
    |   IntegerOrFloatingLiteral
    |   boolenLiteral
    |   QuoteStringLiteral
    |   NULL
    ;

boolenLiteral
    :   TRUE
    |   FALSE
    ;

lambdaParameters
    :   varId
    |   '(' formalOrInferredParameterList? ')'
    ;

formalOrInferredParameterList
    :   formalOrInferredParameter (newlines? ',' newlines? formalOrInferredParameter)*
    ;

formalOrInferredParameter
    :   declType? varId
    ;

// id

assignOperator
    :   EQ
    |   RIGHSHIFT_ASSGIN
    |   URSHIFT_ASSGIN
    |   LSHIFT_ASSGIN
    |   ADD_ASSIGN
    |   SUB_ASSIGN
    |   AND_ASSIGN
    |   OR_ASSIGN
    |   MUL_ASSIGN
    |   MOD_ASSIGN
    |   DIV_ASSIGN
    |   XOR_ASSIGN
    ;

opId
    :   BANG
    |   TILDE
    |   ADD
    |   SUB
    |   INC
    |   DEC
    |   DOTMUL
    |   assignOperator
    |   OPID
    ;

varId
    : ID
    | FUNCTION
    ;