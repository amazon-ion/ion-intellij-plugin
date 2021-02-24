package com.amazon.ion.plugin.intellij.formatting.blocks

import com.intellij.lang.ASTNode

/**
 * Simple Ion Block.
 */
class SimpleIonBlock(
    node: ASTNode,
    formatting: IonBlockFormattingOptions,
    options: IonBlockOptions
) : AbstractIonBlock(node, formatting = formatting, options = options)
