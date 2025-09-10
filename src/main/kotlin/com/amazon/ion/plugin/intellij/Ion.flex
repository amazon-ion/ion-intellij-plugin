package com.amazon.ion.plugin.intellij;

import com.intellij.psi.tree.IElementType;
import com.amazon.ion.plugin.intellij.psi.IonTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;

import static com.amazon.ion.plugin.intellij.helpers.ContentCorrectnessHelper.*;

%%

%class IonLexer
%public
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

%{
    private java.util.Stack<Integer> _zzStateStack = new java.util.Stack<Integer>();
%}
/* Whitespace */
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
InputCharacter = [^\r\n]
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}*
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

/* Global */
Identifier = ([A-Za-z_$])([0-9A-Za-z_$])*
Comma = ","
LBrace = "{"
RBrace = "}"
LBracket = "["
RBracket = "]"
LParen = "("
RParen = ")"

/* Key */
KeyCharacter = [^:\n\r\t\f\\\'\"]

/* Operators */
DoublePlus = "++"
SinglePlus = "+"
DoubleMinus = "--"
SingleMinus = "-"
Multiplication = "*"
Division = "/"
Equality = "="
LeftAngular = "<"
RightAngular = ">"
ShiftLeft = "<<"
ShiftRight = ">>"
OrSign = "|"
ShortCircuitOr = "||"
AndSign = "&"
ShortCircuitAnd = "&&"
DollarSign = "$"
PowerSign = "^"
ParentDir = ".."
CurrentDir = "."
PoundSign = "#"
NegationSign = "!"
Modulo = "\%"
SemiColon = ";"
Elvis = "?"
At = "@"
Backtick = "`"
Tilde = "~"


Operator = {DoublePlus} | {DoubleMinus} | {ShiftLeft} | {ShiftRight} | {ShortCircuitOr} | {ShortCircuitAnd} | {ParentDir} |
           {SinglePlus} | {SingleMinus} | {Multiplication} | {Division} | {PowerSign} | {CurrentDir} |
           {LeftAngular} | {RightAngular} | {Equality} | {OrSign} | {AndSign} | {DollarSign} | {PoundSign} | {NegationSign} |
           {Modulo} | {SemiColon} | {Elvis} | {At} | {Backtick} | {Tilde}

/* Type of values */
Boolean = "true" | "false"

/* Nulls */
Null = "null.null" | "null.bool" | "null.int" | "null.float" | "null.decimal" |
       "null.timestamp" | "null.string" | "null.symbol" | "null.blob" | "null.clob" |
       "null.struct" | "null.list" | "null.sexp" | "null"

/* Integers and floats */
Sign = "-"
HexIntPrefix = "0x"
HexChars = [0-9a-fA-F]
HexVal = {HexIntPrefix}{HexChars}+
Digits = [0-9]+
Integer = {Sign}? {Digits}
HexInteger = {Sign}? {HexVal}
DecimalPoint = ("\." | "d" | "D")
FractionSign = ("+" | "-")
DecimalSeparator = {DecimalPoint} {FractionSign}?
Exponent = ([deDE] {FractionSign}? {Integer})
FloatSpecials = "nan" | "+inf" | "-inf"
Float = ({FloatSpecials}) |
        ({Integer} {DecimalSeparator} [0-9]* {Exponent}?) |
        ({Integer} {Exponent}?)


BinaryPrefix = "0b"
BinaryChars = (0|1)
BinaryInteger = {BinaryPrefix} {BinaryChars}+


/* Strings */
BasicEscapeChars = [\0\a\b\f\n\r\t\v\?\/\\]
HexEscape = "\\x" {HexVal}{HexVal}
FourDigitUnicode = "\\u" {HexVal}{HexVal}{HexVal}{HexVal}
EightDigitUnicode = "\\U" {HexVal}{HexVal}{HexVal}{HexVal}{HexVal}{HexVal}{HexVal}{HexVal}

/* Strings */
Quote = "\'"
QQuote = "\""
QQQuote = "\'\'\'"

NewLineEscape = "\\"
StringEscapes  =  {BasicEscapeChars} | {FourDigitUnicode} | {EightDigitUnicode} | {HexEscape} | {NewLineEscape}

// Everything upto the next '''
// This is broken since a multiline string can contain '''.
// We need a greedy match
QQQChar =  ~{QQQuote}

EscapedQQChar = "\\\""
NonQQChar = [^\"\n\r]
QQChar =  ({StringEscapes}|{EscapedQQChar}|{NonQQChar})+

EscapedQChar = "\\\'"
NonQChar = [^\'\n\r]
QChar =  ({NonQChar}|{StringEscapes}|{EscapedQChar})

/* Blobs and clobs */
LobStart = "{{"
LobEnd = "}}"

BlobChar = [A-Za-z0-9+/]
BlobPaddingValue = "="
BlobValue = {BlobChar}+ {BlobPaddingValue}*

/* Date and time */
DateTimeSeparator = "T"
DateSeparator = "-"
TimeSeparator = ":"
MillisSeparator = "."
OffsetSign = "+" | "-"
Zulu = "Z"
TimezoneOffset = ({OffsetSign}{Hour}{TimeSeparator}{Minutes})
Year = [0-9][0-9][0-9][0-9]
Month = [0-9][0-9]
Day = [0-9][0-9]
Hour = [0-9][0-9]
Minutes = [0-9][0-9]
Seconds = [0-9][0-9]
Milliseconds = [0-9][0-9]*

TimeValue = ({Hour}{TimeSeparator}{Minutes}{TimeSeparator}{Seconds}{MillisSeparator}{Milliseconds}) |
            ({Hour}{TimeSeparator}{Minutes}{TimeSeparator}{Seconds}) |
            ({Hour}{TimeSeparator}{Minutes})

DateValue = ({Year}{DateSeparator}{Month}{DateSeparator}{Day}{DateTimeSeparator}?) |
            ({Year}{DateSeparator}{Month}{DateTimeSeparator}) |
            ({Year}{DateTimeSeparator})

TimeStamp = ({DateValue}{TimeValue}{TimezoneOffset}) |
            ({DateValue}{TimeValue}{Zulu}) |
            ({DateValue}{TimeValue}) |
            ({DateValue})


/* Annotations */
Separator = [:]
AnnotationSeparator = "::"

%state ION_LIST ION_STRUCT READ_VALUE READ_KEY S_EXP READ_LOB READ_QQQ_STR READ_QQ_STR READ_Q_STR

%%


<READ_QQQ_STR> {
    {QQQuote}                                              { yybegin(_zzStateStack.pop()); return IonTypes.QQQ_END;}
    {QQQChar}                                              { yypushback(3); return IonTypes.QQQ_VALUE;}
}

<READ_QQ_STR> {
    {QQuote}                                               { yybegin(_zzStateStack.pop()); return IonTypes.QQ_END;}
    {QQChar}                                               { return IonTypes.QQ_VALUE;}
}

<READ_Q_STR> {
    {Quote}                                                { yybegin(_zzStateStack.pop()); return IonTypes.Q_END;}
    {QChar}+                                               { return IonTypes.Q_VALUE;}
}

<READ_LOB> {
    {LobEnd}                                               { yybegin(_zzStateStack.pop()); return IonTypes.LOB_END;}
    {QQQuote}                                              { _zzStateStack.push(yystate()); yybegin(READ_QQQ_STR); return IonTypes.QQQ_START;}
    {QQuote}                                               { _zzStateStack.push(yystate()); yybegin(READ_QQ_STR); return IonTypes.QQ_START;}
    {BlobValue}                                            { return (isValidBase64(yytext())) ? IonTypes.BLOB_VALUE : TokenType.BAD_CHARACTER; }
    {Identifier}                                           { return IonTypes.BAD_CHARACTER; }
}

<READ_VALUE> {
    {LobStart}                                              { yybegin(READ_LOB); return IonTypes.LOB_START;}
    {AnnotationSeparator}                                   { return IonTypes.ANNOTATION_SEPARATOR;}
    {LBrace}                                                { yybegin(ION_STRUCT); return IonTypes.LBRACE;}
    {LBracket}                                              { yybegin(ION_LIST); return IonTypes.LBRACKET;}
    {LParen}                                                { yybegin(S_EXP); return IonTypes.LPAREN;}
    {Boolean}                                               { yybegin(_zzStateStack.pop()); return IonTypes.BOOLEAN;}
    {Null}                                                  { yybegin(_zzStateStack.pop()); return IonTypes.NULL;}
    {TimeStamp}                                             { yybegin(_zzStateStack.pop()); return IonTypes.TIMESTAMP;}
    {HexInteger}                                            { yybegin(_zzStateStack.pop()); return IonTypes.HEXINT;}
    {Integer}                                               { yybegin(_zzStateStack.pop()); return IonTypes.INTEGER;}
    {Float}                                                 { yybegin(_zzStateStack.pop()); return IonTypes.DECIMAL;}
    {QQQuote}                                               { yybegin(READ_QQQ_STR); return IonTypes.QQQ_START;}
    {QQuote}                                                { yybegin(READ_QQ_STR); return IonTypes.QQ_START;}
    {Quote}                                                 { yybegin(READ_Q_STR); return IonTypes.Q_START;}
    {Identifier}+                                           { yybegin(_zzStateStack.pop()); return IonTypes.IDENTIFIER;}

}

<READ_KEY> {
    {QQQuote}                                               { yybegin(_zzStateStack.pop()); return IonTypes.QQQUOTE;}
    {QQuote}                                                { yybegin(_zzStateStack.pop()); return IonTypes.QQUOTE;}
    {Quote}                                                 { yybegin(_zzStateStack.pop()); return IonTypes.QUOTE;}
    {KeyCharacter}+                                         { return IonTypes.KEY_NAME;}
}

<ION_STRUCT> {
    {Comment}                                               { return IonTypes.COMMENT;}
    {AnnotationSeparator}                                   { _zzStateStack.push(yystate()); yybegin(READ_VALUE); return IonTypes.ANNOTATION_SEPARATOR;}
    {RBrace}                                                { yybegin(_zzStateStack.pop()); return IonTypes.RBRACE;}
    {QQQuote}                                               { _zzStateStack.push(yystate()); yybegin(READ_KEY); return IonTypes.QQQUOTE;}
    {QQuote}                                                { _zzStateStack.push(yystate()); yybegin(READ_KEY); return IonTypes.QQUOTE;}
    {Quote}                                                 { _zzStateStack.push(yystate()); yybegin(READ_KEY); return IonTypes.QUOTE;}
    {Separator}                                             { _zzStateStack.push(yystate()); yybegin(READ_VALUE); return IonTypes.SEPARATOR;}
    {Identifier}+                                           { return IonTypes.KEY_NAME;}
}


<S_EXP> {
    {Comment}                                               { return IonTypes.COMMENT;}
    {AnnotationSeparator}                                   { return IonTypes.ANNOTATION_SEPARATOR;}
    {LobStart}                                              { _zzStateStack.push(yystate()); yybegin(READ_LOB); return IonTypes.LOB_START;}
    {LParen}                                                { _zzStateStack.push(yystate()); yybegin(S_EXP); return IonTypes.LPAREN;}
    {RParen}                                                { yybegin(_zzStateStack.pop()); return IonTypes.RPAREN;}
    {Boolean}                                               { return IonTypes.BOOLEAN;}
    {Null}                                                  { return IonTypes.NULL;}
    {TimeStamp}                                             { return IonTypes.TIMESTAMP;}
    {HexInteger}                                            { return IonTypes.HEXINT;}
    {Integer}                                               { return IonTypes.INTEGER;}
    {Float}                                                 { return IonTypes.DECIMAL;}
    {Operator}                                              { return IonTypes.OPERATOR;}
    {QQQuote}                                               { _zzStateStack.push(yystate()); yybegin(READ_QQQ_STR); return IonTypes.QQQ_START;}
    {QQuote}                                                { _zzStateStack.push(yystate()); yybegin(READ_QQ_STR); return IonTypes.QQ_START;}
    {Quote}                                                 { _zzStateStack.push(yystate()); yybegin(READ_Q_STR); return IonTypes.Q_START;}
    {Identifier}+                                           { return IonTypes.IDENTIFIER;}
}

<ION_LIST> {
    {Comment}                                               { return IonTypes.COMMENT;}
    {RBracket}                                              { yybegin(_zzStateStack.pop()); return IonTypes.RBRACKET;}
    {Boolean}                                               { return IonTypes.BOOLEAN;}
    {Null}                                                  { return IonTypes.NULL;}
    {TimeStamp}                                             { return IonTypes.TIMESTAMP;}
    {HexInteger}                                            { return IonTypes.HEXINT;}
    {Integer}                                               { return IonTypes.INTEGER;}
    {Float}                                                 { return IonTypes.DECIMAL;}
    {QQQuote}                                               { _zzStateStack.push(yystate()); yybegin(READ_QQQ_STR); return IonTypes.QQQ_START;}
    {QQuote}                                                { _zzStateStack.push(yystate()); yybegin(READ_QQ_STR); return IonTypes.QQ_START;}
    {Quote}                                                 { _zzStateStack.push(yystate()); yybegin(READ_Q_STR); return IonTypes.Q_START;}
    {Identifier}+                                           { return IonTypes.IDENTIFIER;}
}

<YYINITIAL> {
    {Comment}                                              { return IonTypes.COMMENT;}
}

{AnnotationSeparator}                                       { return IonTypes.ANNOTATION_SEPARATOR;}

{Comma}                                                     { return IonTypes.COMMA;}
{Boolean}                                                   { return IonTypes.BOOLEAN;}
{TimeStamp}                                                 { return IonTypes.TIMESTAMP;}
{Null}                                                      { return IonTypes.NULL;}
{BinaryInteger}                                             { return IonTypes.BININT; }
{HexInteger}                                                { return IonTypes.HEXINT;}
{Integer}                                                   { return IonTypes.INTEGER;}
{Float}                                                     { return IonTypes.DECIMAL;}

{LobStart}                                                  { _zzStateStack.push(yystate()); yybegin(READ_LOB); return IonTypes.LOB_START;}
{LBracket}                                                  { _zzStateStack.push(yystate()); yybegin(ION_LIST); return IonTypes.LBRACKET;}
{LBrace}                                                    { _zzStateStack.push(yystate()); yybegin(ION_STRUCT); return IonTypes.LBRACE;}
{LParen}                                                    { _zzStateStack.push(yystate()); yybegin(S_EXP); return IonTypes.LPAREN;}
{QQQuote}                                                   { _zzStateStack.push(yystate()); yybegin(READ_QQQ_STR); return IonTypes.QQQ_START;}
{QQuote}                                                    { _zzStateStack.push(yystate()); yybegin(READ_QQ_STR); return IonTypes.QQ_START;}
{Quote}                                                     { _zzStateStack.push(yystate()); yybegin(READ_Q_STR); return IonTypes.Q_START;}

{WhiteSpace}+                                               { return TokenType.WHITE_SPACE; }
{Identifier}+                                               { return IonTypes.IDENTIFIER;}
.                                                           { return TokenType.BAD_CHARACTER; }
