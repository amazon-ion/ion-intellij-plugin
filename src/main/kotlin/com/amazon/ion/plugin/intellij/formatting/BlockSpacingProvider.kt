package com.amazon.ion.plugin.intellij.formatting

import com.intellij.formatting.Block
import com.intellij.formatting.Spacing

/**
 * Block interface for retrieving block spacing.
 */
interface BlockSpacingProvider {
    fun getSpacing(parent: Block, childA: Block?, childB: Block): Spacing?
}
