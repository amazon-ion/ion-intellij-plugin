package com.amazon.ion.plugin.intellij.highlight

import com.amazon.ion.plugin.intellij.psi.IonTypes.ANNOTATION
import com.amazon.ion.plugin.intellij.psi.IonTypes.BININT
import com.amazon.ion.plugin.intellij.psi.IonTypes.BLOB_VALUE
import com.amazon.ion.plugin.intellij.psi.IonTypes.BOOLEAN
import com.amazon.ion.plugin.intellij.psi.IonTypes.COMMA
import com.amazon.ion.plugin.intellij.psi.IonTypes.COMMENT
import com.amazon.ion.plugin.intellij.psi.IonTypes.DECIMAL
import com.amazon.ion.plugin.intellij.psi.IonTypes.EXPRESSION_OPERATOR
import com.amazon.ion.plugin.intellij.psi.IonTypes.HEXINT
import com.amazon.ion.plugin.intellij.psi.IonTypes.IDENTIFIER
import com.amazon.ion.plugin.intellij.psi.IonTypes.INTEGER
import com.amazon.ion.plugin.intellij.psi.IonTypes.KEY_NAME
import com.amazon.ion.plugin.intellij.psi.IonTypes.LBRACE
import com.amazon.ion.plugin.intellij.psi.IonTypes.LBRACKET
import com.amazon.ion.plugin.intellij.psi.IonTypes.LOB_END
import com.amazon.ion.plugin.intellij.psi.IonTypes.LOB_START
import com.amazon.ion.plugin.intellij.psi.IonTypes.LPAREN
import com.amazon.ion.plugin.intellij.psi.IonTypes.NULL
import com.amazon.ion.plugin.intellij.psi.IonTypes.OPERATOR
import com.amazon.ion.plugin.intellij.psi.IonTypes.QQQ_END
import com.amazon.ion.plugin.intellij.psi.IonTypes.QQQ_START
import com.amazon.ion.plugin.intellij.psi.IonTypes.QQQ_VALUE
import com.amazon.ion.plugin.intellij.psi.IonTypes.QQ_END
import com.amazon.ion.plugin.intellij.psi.IonTypes.QQ_START
import com.amazon.ion.plugin.intellij.psi.IonTypes.QQ_VALUE
import com.amazon.ion.plugin.intellij.psi.IonTypes.Q_END
import com.amazon.ion.plugin.intellij.psi.IonTypes.Q_START
import com.amazon.ion.plugin.intellij.psi.IonTypes.Q_VALUE
import com.amazon.ion.plugin.intellij.psi.IonTypes.RBRACE
import com.amazon.ion.plugin.intellij.psi.IonTypes.RBRACKET
import com.amazon.ion.plugin.intellij.psi.IonTypes.RPAREN
import com.amazon.ion.plugin.intellij.psi.IonTypes.SEPARATOR
import com.amazon.ion.plugin.intellij.psi.IonTypes.TIMESTAMP
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.TokenType.BAD_CHARACTER
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

/**
 * Syntax highlight object which contains all the syntax highlight attribute names.
 */
object IonSyntaxHighlight {

    /**
     * Attributes associate specific tokens with color attributes.
     */
    object Attributes {

        val IonOperators: TextAttributesKey = TextAttributesKey.createTextAttributesKey("ION_OPERATORS")

        val IonSymbol: TextAttributesKey = DefaultLanguageHighlighterColors.KEYWORD
        val BlobBraces: TextAttributesKey = DefaultLanguageHighlighterColors.KEYWORD
        val IonBlob: TextAttributesKey = DefaultLanguageHighlighterColors.CONSTANT
        val IonExpressionOperators: TextAttributesKey = DefaultLanguageHighlighterColors.STATIC_METHOD
        val IonString: TextAttributesKey = DefaultLanguageHighlighterColors.STRING
        val Separator: TextAttributesKey = DefaultLanguageHighlighterColors.OPERATION_SIGN
        val Keyword: TextAttributesKey = DefaultLanguageHighlighterColors.KEYWORD
        val StructKey: TextAttributesKey = DefaultLanguageHighlighterColors.INSTANCE_FIELD
        val IonTimeStamp: TextAttributesKey = DefaultLanguageHighlighterColors.NUMBER
        val IonNumber: TextAttributesKey = DefaultLanguageHighlighterColors.NUMBER
        val IonBool: TextAttributesKey = DefaultLanguageHighlighterColors.KEYWORD
        val Comment: TextAttributesKey = DefaultLanguageHighlighterColors.BLOCK_COMMENT
        val Brackets: TextAttributesKey = DefaultLanguageHighlighterColors.BRACKETS
        val Braces: TextAttributesKey = DefaultLanguageHighlighterColors.BRACES
        val Annotation: TextAttributesKey = DefaultLanguageHighlighterColors.METADATA
        val Parenthesis: TextAttributesKey = DefaultLanguageHighlighterColors.PARENTHESES
        val Comma: TextAttributesKey = DefaultLanguageHighlighterColors.COMMA
        val BadCharacter: TextAttributesKey = HighlighterColors.BAD_CHARACTER
    }

    /**
     * Maps token types to a list of attributes.
     */
    val Tokens = mapOf(
        tokenSet(SEPARATOR) to arrayOf(Attributes.Separator),
        tokenSet(KEY_NAME) to arrayOf(Attributes.StructKey),
        tokenSet(EXPRESSION_OPERATOR) to arrayOf(Attributes.IonExpressionOperators),
        tokenSet(INTEGER, DECIMAL, HEXINT, BININT) to arrayOf(Attributes.IonNumber),
        tokenSet(BOOLEAN, NULL) to arrayOf(Attributes.IonBool),
        tokenSet(ANNOTATION) to arrayOf(Attributes.Annotation),
        tokenSet(Q_START, Q_END, Q_VALUE, IDENTIFIER) to arrayOf(Attributes.IonSymbol),
        tokenSet(QQ_START, QQ_END, QQ_VALUE, QQQ_START, QQQ_END, QQQ_VALUE) to arrayOf(Attributes.IonString),
        tokenSet(LBRACE, RBRACE) to arrayOf(Attributes.Braces),
        tokenSet(LBRACKET, RBRACKET) to arrayOf(Attributes.Brackets),
        tokenSet(LPAREN, RPAREN) to arrayOf(Attributes.Parenthesis),
        tokenSet(COMMENT) to arrayOf(Attributes.Comment),
        tokenSet(TIMESTAMP) to arrayOf(Attributes.IonTimeStamp),
        tokenSet(BAD_CHARACTER) to arrayOf(Attributes.BadCharacter),
        tokenSet(BLOB_VALUE) to arrayOf(Attributes.IonBlob),
        tokenSet(LOB_START, LOB_END) to arrayOf(Attributes.BlobBraces),
        tokenSet(COMMA) to arrayOf(Attributes.Comma),
        tokenSet(OPERATOR) to arrayOf(Attributes.IonOperators)
    )
}

private fun tokenSet(vararg tokens: IElementType) = TokenSet.create(*tokens)
