package com.amazon.ion.plugin.intellij.highlight

import com.amazon.ion.plugin.intellij.IonLexerAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class IonSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = IonLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> =
        IonSyntaxHighlight.Tokens.asSequence().firstOrNull { it.key.contains(tokenType) }?.value
            ?: EmptyKeys
}

val EmptyKeys = emptyArray<TextAttributesKey>()
