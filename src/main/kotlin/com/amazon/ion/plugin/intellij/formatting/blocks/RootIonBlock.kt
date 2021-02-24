package com.amazon.ion.plugin.intellij.formatting.blocks

import com.intellij.formatting.Alignment
import com.intellij.lang.ASTNode

class RootIonBlock(node: ASTNode, options: IonBlockOptions) :
    AbstractIonBlock(node,
        formatting = IonBlockFormattingOptions(alignment = Alignment.createAlignment()),
        options = options
    )
