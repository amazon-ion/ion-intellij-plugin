package com.amazon.ion.plugin.intellij.formatting.blocks

import com.amazon.ion.plugin.intellij.formatting.BlockSpacingProvider
import com.amazon.ion.plugin.intellij.utils.childSequence
import com.amazon.ion.plugin.intellij.utils.filterWhitespace
import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.ChildAttributes
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.formatting.Wrap
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.IElementType

/**
 * Base Ion Block construct.
 */
abstract class AbstractIonBlock(
    node: ASTNode,
    protected val formatting: IonBlockFormattingOptions,
    protected val options: IonBlockOptions
) : AbstractBlock(node, formatting.wrap, formatting.alignment) {

    /**
     * Types which are considered "container" types, for example in a struct, there are two container types.
     * Member and Pair because the PSI tree of a Struct is as follows:
     * Struct
     * - Members
     * -- Pairs
     * --- Key + Seperator + Value
     */
    protected open val childContainerTypes: Set<IElementType> = emptySet()

    /**
     * Types which wrap are expected to wrap a container for example a struct:
     * { <- LBRACE
     *      value: test
     * } <- RBRACE
     *
     * Wrap a container and hence should not inherit the child formatting options.
     */
    protected open val containerWrapperTypes: Set<IElementType> = emptySet()

    /**
     * Child types which should be indented, i.e. a comment as a child of a struct should be indent,
     * along with a pair as a child of a struct.
     */
    protected open val childIndentedTypes: Set<IElementType> = emptySet()

    /**
     * Alignment which is passed on to children of this block.
     */
    protected val childAlignment: Alignment = Alignment.createAlignment()

    override fun getIndent(): Indent? = formatting.indent

    override fun getSpacing(child1: Block?, child2: Block): Spacing? = options.spaceBuilder.getSpacing(this, child1, child2)

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes =
        when {
            // If this block is a "container" type block, like a list, struct, or expression then the children
            // will always have an indent.
            childContainerTypes.isNotEmpty() -> ChildAttributes(Indent.getNormalIndent(), childAlignment)
            else -> super.getChildAttributes(newChildIndex)
        }

    override fun isLeaf(): Boolean = node.firstChildNode == null

    protected open fun buildChildBlockFormatting(child: ASTNode) =
        when {
            containerWrapperTypes.contains(child.elementType) ->
                IonBlockFormatting.sameAlignment(this)
            childIndentedTypes.contains(child.elementType) ->
                IonBlockFormattingOptions(
                    alignment = childAlignment,
                    indent = Indent.getNormalIndent()
                )
            else ->
                IonBlockFormattingOptions(
                    alignment = alignment,
                    indent = Indent.getNoneIndent()
                )
        }

    /**
     * Build any child blocks.
     */
    override fun buildChildren(): MutableList<Block> = buildChildrenOf(node).toMutableList()

    /**
     * Recursively build children blocks skip any blocks which are expected within the particular container.
     *
     * For example for structs, we only build blocks for key - value pairs.
     */
    private fun buildChildrenOf(node: ASTNode): Sequence<Block> =
        node.childSequence().filterWhitespace()
            .flatMap {
                when {
                    childContainerTypes.contains(it.elementType) -> buildChildrenOf(it)
                    else -> sequenceOf(
                        IonBlockFactory.create(it, buildChildBlockFormatting(it), options)
                    )
                }
            }
}

object IonBlockFormatting {
    val None = IonBlockFormattingOptions(wrap = null, alignment = null)

    fun sameAlignment(parent: AbstractIonBlock) = IonBlockFormattingOptions(
        wrap = null, alignment = parent.alignment
    )
}

data class IonBlockFormattingOptions(
    val wrap: Wrap? = null,
    val alignment: Alignment? = null,
    val indent: Indent? = null
)

data class IonBlockOptions(
    val spaceBuilder: BlockSpacingProvider,
    val codeStyle: CodeStyleSettings
)
