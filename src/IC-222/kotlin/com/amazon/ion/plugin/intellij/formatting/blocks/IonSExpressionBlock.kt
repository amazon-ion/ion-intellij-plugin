package com.amazon.ion.plugin.intellij.formatting.blocks

import com.amazon.ion.plugin.intellij.formatting.previousSibling
import com.amazon.ion.plugin.intellij.formatting.sameLineAs
import com.amazon.ion.plugin.intellij.psi.IonTypes
import com.amazon.ion.plugin.intellij.psi.isOneLiner
import com.amazon.ion.plugin.intellij.utils.elementIsA
import com.intellij.lang.ASTNode
import com.intellij.openapi.diagnostic.debug
import com.intellij.openapi.diagnostic.logger
import com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.idea.core.util.getLineNumber

private val logger = logger<IonSExpressionBlock>()

class IonSExpressionBlock(
    node: ASTNode,
    formatting: IonBlockFormattingOptions,
    options: IonBlockOptions
) : AbstractIonBlock(node, formatting = formatting, options = options) {

    override val childIndentedTypes: Set<IElementType> = setOf(
        IonTypes.SEXPRESSION_ATOM,
        IonTypes.COMMENT
    )

    override val childContainerTypes: Set<IElementType> = setOf(
        IonTypes.SEXPRESSION_ELEMENTS
    )

    override val containerWrapperTypes: Set<IElementType> = setOf(
        IonTypes.LPAREN,
        IonTypes.RPAREN
    )
}
