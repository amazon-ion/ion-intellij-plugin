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
        IonTypes.VALUE,
        IonTypes.COMMENT
    )

    override val childContainerTypes: Set<IElementType> = setOf(
        IonTypes.SEXPRESSION_ELEMENTS
    )

    override val containerWrapperTypes: Set<IElementType> = setOf(
        IonTypes.LPAREN,
        IonTypes.RPAREN
    )

    override fun buildChildBlockFormatting(child: ASTNode): IonBlockFormattingOptions =
        buildSpecialCaseChildBlockFormatting(child) ?:
            super.buildChildBlockFormatting(child)

    private fun buildSpecialCaseChildBlockFormatting(child: ASTNode): IonBlockFormattingOptions? {

        // Lazy evaluate the previous sibling if needed.
        val previous by lazy { child.previousSibling() }

        /**
         * Check if we are the first comment within the expression, there is a special comment
         * case where we don't want to apply the child alignment to the comment. For example:
         *
         * (join // special case comment which is inline with operator
         *   // child comments are inline with inner values
         *   anotherValue
         * )
         */
        if (child elementIsA IonTypes.COMMENT && previous?.elementType == IonTypes.SEXPRESSION_OPERATOR) {

            logger.debug { "Formatting [${child.psi.getLineNumber()}] - Special case inline expression comment line" }

            val comment = child.psi
            val expressionOperator = previous!!

            if (comment.isOneLiner() && child.sameLineAs(expressionOperator)) {
                return IonBlockFormatting.sameAlignment(this)
            }
        }

        return null
    }
}
