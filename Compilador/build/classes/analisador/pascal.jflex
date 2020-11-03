/*-***
 *
 * This file defines a stand-alone lexical analyzer for a subset of the Pascal
 * programming language.  This is the same lexer that will later be integrated
 * with a CUP-based parser.  Here the lexer is driven by the simple Java test
 * program in ./PascalLexerTest.java, q.v.  See 330 Lecture Notes 2 and the
 * Assignment 2 writeup for further discussion.
 *
 */

package analisadorlexico;
import analisadorlexico.sym;
import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%cup
%line
%column
%unicode
%class PascalLexer

%{

/**
 * Return a new Symbol with the given token id, and with the current line and
 * column numbers.
 */
Symbol newSym(int tokenId) {
    return new Symbol(tokenId, yyline, yycolumn);
}

/**
 * Return a new Symbol with the given token id, the current line and column
 * numbers, and the given token value.  The value is used for tokens such as
 * identifiers and numbers.
 */
Symbol newSym(int tokenId, Object value) {
    return new Symbol(tokenId, yyline, yycolumn, value);
}

%}


/*-*
 * PATTERN DEFINITIONS:
 */
letter          = [A-Za-z]
digit           = [0-9]
alphanumeric    = {letter}|{digit}
other_id_char   = [_]
identifier      = {letter}({alphanumeric}|{other_id_char})*
integer         = {digit}*
real            = {integer}\.{integer}
char            = '.'
leftbrace       = \{
rightbrace      = \}
nonrightbrace   = [^}]
comment_body    = {nonrightbrace}*
comment         = {leftbrace}{comment_body}{rightbrace}
whitespace      = [ \n\t]
comment_line    = "//".*

%%
/**
 * LEXICAL RULES:
 */
and             { return newSym(sym.AND); }
array           { return newSym(sym.ARRAY); }
begin           { return newSym(sym.BEGIN); }
else            { return newSym(sym.ELSE); }
end             { return newSym(sym.END); }
if              { return newSym(sym.IF); }
of              { return newSym(sym.OF); }
or              { return newSym(sym.OR); }
program         { return newSym(sym.PROGRAM); }
procedure       { return newSym(sym.PROCEDURE); }
then            { return newSym(sym.THEN); }
type            { return newSym(sym.TYPE); }
var             { return newSym(sym.VAR); }
int             { return newSym(sym.VARINTEGER); }
boolean         { return newSym(sym.VARBOOLEAN); }
true            { return newSym(sym.TRUE); }
false           { return newSym(sym.FALSE); }
not             { return newSym(sym.NOT); }
while           { return newSym(sym.WHILE); }
write           { return newSym(sym.WRITE); }
read            { return newSym(sym.READ); }
do              { return newSym(sym.DO); }
"<>"            { return newSym(sym.LESSGTR); }
"*"             { return newSym(sym.TIMES); }
"+"             { return newSym(sym.PLUS); }
"-"             { return newSym(sym.MINUS); }
div             { return newSym(sym.DIVIDE); }
";"             { return newSym(sym.SEMI); }
","             { return newSym(sym.COMMA); }
"("             { return newSym(sym.LEFT_PAREN); }
")"             { return newSym(sym.RT_PAREN); }
"["             { return newSym(sym.LEFT_BRKT); }
"]"             { return newSym(sym.RT_BRKT); }
"="             { return newSym(sym.EQ); }
"<"             { return newSym(sym.GTR); }
">"             { return newSym(sym.LESS); }
"<="            { return newSym(sym.LESS_EQ); }
">="            { return newSym(sym.GTR_EQ); }
"!="            { return newSym(sym.NOT_EQ); }
":"             { return newSym(sym.COLON); }
":="            { return newSym(sym.ASSMNT); }
"."             { return newSym(sym.DOT); }
{identifier}    { if (yylength() > 15)
                    return newSym(sym.IDENT, yytext().substring(0,15));
                  else
                    return newSym(sym.IDENT, yytext()); }
{integer}       { if (yylength() > 9)
                    return newSym(sym.INT, new Integer(yytext().substring(0,9)));
                  else
                    return newSym(sym.INT, new Integer(yytext())); }
{real}          { if (yylength() > 9)
                    return newSym(sym.REAL, new Double(yytext().substring(0,9)));
                  else
                    return newSym(sym.REAL, new Double(yytext())); }
{char}          { return newSym(sym.CHAR, new Character(yytext().charAt(1))); }
{comment}       { /* For this stand-alone lexer, print out comments. */
                  System.out.println("Recognized comment: " + yytext()); }
{comment_line}  {}
{whitespace}    { /* Ignore whitespace. */ }
.               { return newSym(sym.ILLEGAL, Character.toString(yytext().charAt(0))); }