package com.amazon.ion.plugin.intellij.formatting.blocks

import com.amazon.ion.plugin.intellij.psi.IonTypes.ARRAY
import com.amazon.ion.plugin.intellij.psi.IonTypes.EXPRESSION
import com.amazon.ion.plugin.intellij.psi.IonTypes.STRUCT
import com.intellij.formatting.Block
import com.intellij.lang.ASTNode

/**
 * Factory which creates the correct type of IonBlock.
 */
object IonBlockFactory {

    fun create(
        node: ASTNode,
        formatting: IonBlockFormattingOptions,
        options: IonBlockOptions
    ): Block =
        when (node.elementType) {
            STRUCT -> IonStructBlock(node, formatting, options)
            ARRAY -> IonListBlock(node, formatting, options)
            EXPRESSION -> IonSexpBlock(node, formatting, options)
            else -> SimpleIonBlock(node, formatting, options)
        }
}
