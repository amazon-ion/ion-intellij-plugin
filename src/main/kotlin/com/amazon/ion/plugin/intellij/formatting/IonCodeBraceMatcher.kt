package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.IonLanguage
import com.amazon.ion.plugin.intellij.psi.IonTypes.LBRACE
import com.amazon.ion.plugin.intellij.psi.IonTypes.LBRACKET
import com.amazon.ion.plugin.intellij.psi.IonTypes.LOB_END
import com.amazon.ion.plugin.intellij.psi.IonTypes.LOB_START
import com.amazon.ion.plugin.intellij.psi.IonTypes.LPAREN
import com.amazon.ion.plugin.intellij.psi.IonTypes.RBRACE
import com.amazon.ion.plugin.intellij.psi.IonTypes.RBRACKET
import com.amazon.ion.plugin.intellij.psi.IonTypes.RPAREN
import com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

/**
 * Adapter into the Intellij plugin extension.
 */
class IonCodeBraceMatcherAdapter : PairedBraceMatcherAdapter(IonCodeBraceMatcher(), IonLanguage.INSTANCE)

/**
 * Matches open and closing braces for IonCode
 */
class IonCodeBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> = BracePairs

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?
    ): Boolean = true

    override fun getCodeConstructStart(
        file: PsiFile?,
        openingBraceOffset: Int
    ): Int = openingBraceOffset
}

/**
 * List of brace pairs for Ion.
 */
private val BracePairs = arrayOf(
    bracePair(LBRACE, RBRACE),
    bracePair(LBRACKET, RBRACKET),
    bracePair(LPAREN, RPAREN),
    bracePair(LOB_START, LOB_END)
)

private fun bracePair(left: IElementType, right: IElementType) =
    BracePair(left, right, true)
