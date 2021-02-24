package com.amazon.ion.plugin.intellij.formatting.blocks

import com.amazon.ion.plugin.intellij.psi.IonTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType

class IonListBlock(
    node: ASTNode,
    formatting: IonBlockFormattingOptions,
    options: IonBlockOptions
) : AbstractIonBlock(node, formatting = formatting, options = options) {
    override val childIndentedTypes: Set<IElementType> = setOf(
        IonTypes.VALUE,
        IonTypes.COMMENT
    )

    override val childContainerTypes: Set<IElementType> = setOf(
        IonTypes.ELEMENTS
    )

    override val containerWrapperTypes: Set<IElementType> = setOf(
        IonTypes.LBRACKET,
        IonTypes.RBRACKET
    )
}
