package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.IonLanguage
import com.amazon.ion.plugin.intellij.formatting.blocks.IonListBlock
import com.amazon.ion.plugin.intellij.formatting.blocks.IonSexpBlock
import com.amazon.ion.plugin.intellij.formatting.blocks.IonStructBlock
import com.amazon.ion.plugin.intellij.psi.IonTypes
import com.intellij.formatting.Block
import com.intellij.formatting.Spacing
import com.intellij.formatting.SpacingBuilder
import com.intellij.psi.codeStyle.CodeStyleSettings

/**
 * Spacer for Ion Code.
 */
class IonCodeBlockSpacingProvider(settings: CodeStyleSettings) : BlockSpacingProvider {

    private val delegateSpacingBuilder = createSpaceBuilder(settings)

    override fun getSpacing(parent: Block, childA: Block?, childB: Block): Spacing? {
        // Check for special cases and if not then delegate to the inner spacing builder.
        return tinyInlineContainers(parent, childA, childB) ?:
                delegateSpacingBuilder.getSpacing(parent, childA, childB)
    }

    /**
     * Handles spacing for tiny inline containers, for example:
     *
     * container: [] <- in this case we probably don't want to a new line between the braces []
     *
     * TODO: Extend this to support containers that contain a "small amount" of content like this:
     * ( something_small )
     *
     * In these cases we should probably leave this as a one liner.
     */
    private fun tinyInlineContainers(parent: Block, childA: Block?, childB: Block): Spacing? =
        if (ParentContainerTypes.contains(parent::class) && parent.subBlocks.count() == 2 && childA != null) {
            Spacing.createSpacing(0, 1, 0, false, 0)
        } else {
            null
        }
}

private val ParentContainerTypes = setOf(IonListBlock::class, IonSexpBlock::class, IonStructBlock::class)

/**
 * Builds the standard rule based spacing builder.
 */
private fun createSpaceBuilder(settings: CodeStyleSettings) =
    SpacingBuilder(settings, IonLanguage.INSTANCE).apply {

        /**
         * List spacing
         *
         * For lists, we want line breaks after the opening bracket and after lists members. For example:
         * [
         *  <lineBreak> value,
         *  <lineBreak> another,
         *  <lineBreak> // Comment
         * <lineBreak> ]
         */
        after(IonTypes.LBRACKET).lineBreakInCode()
        before(IonTypes.RBRACKET).lineBreakInCode()
        afterInside(IonTypes.COMMA, IonTypes.ARRAY).lineBreakInCode()

        /**
         * Struct spacing
         *
         * For structs, we want line breaks after the opening brace and after struct members. For example:
         *
         * {
         *  <lineBreak> value: true,
         *  <lineBreak> // Comment
         * <lineBreak> }
         */

        after(IonTypes.LBRACE).lineBreakInCode()
        before(IonTypes.RBRACE).lineBreakInCode()
        beforeInside(IonTypes.COMMA, IonTypes.STRUCT).none()
        afterInside(IonTypes.COMMA, IonTypes.STRUCT).lineBreakInCode()

        /**
         * Sexp spacing
         *
         * For Sexps, if we have an expression operator then we want those to be right next to the opening parenthesis like this:
         *  (expression_operator
         *      somethingElse
         *      "anotherValue
         *  )
         *
         *  We also want line breaks between elements within the sexp and between the last element and the last parenthesis, like this:
         *  (expression_operator
         *      <lineBreak> somethingElse
         *      <lineBreak> "anotherValue"
         *  <lineBreak> )
         */

        before(IonTypes.EXPRESSION_OPERATOR).none()
        before(IonTypes.RPAREN).lineBreakInCode()
        beforeInside(IonTypes.VALUE, IonTypes.EXPRESSION).lineBreakInCode()

        /**
         * Separator spacing, by default no space before and one space after separator, like JSON.
         *
         * { example: "space after separator but not before" }
         */
        before(IonTypes.SEPARATOR).none()
        after(IonTypes.SEPARATOR).spaces(1)
    }
