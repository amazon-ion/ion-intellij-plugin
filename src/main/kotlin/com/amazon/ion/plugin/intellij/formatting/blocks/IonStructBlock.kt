package com.amazon.ion.plugin.intellij.formatting.blocks

import com.amazon.ion.plugin.intellij.psi.IonTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType

class IonStructBlock(
    node: ASTNode,
    formatting: IonBlockFormattingOptions,
    options: IonBlockOptions
) : AbstractIonBlock(node, formatting = formatting, options = options) {

    override val childIndentedTypes: Set<IElementType> = setOf(
        IonTypes.PAIR,
        IonTypes.COMMENT
    )

    override val childContainerTypes: Set<IElementType> = setOf(
        IonTypes.MEMBERS
    )

    override val containerWrapperTypes: Set<IElementType> = setOf(
        IonTypes.LBRACE,
        IonTypes.RBRACE
    )
}
