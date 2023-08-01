package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.formatting.blocks.IonBlockOptions
import com.amazon.ion.plugin.intellij.formatting.blocks.RootIonBlock
import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider

/**
 * Creates the block model for an Ion file.
 *
 * The block model will determine how elements are spaced, indented and aligned.
 */
class IonFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val element = formattingContext.psiElement
        val settings = formattingContext.codeStyleSettings

        val rootBlock = RootIonBlock(
            node = element.node,
            options = IonBlockOptions(
                spaceBuilder = IonCodeBlockSpacingProvider(settings),
                codeStyle = settings
            )
        )


        return FormattingModelProvider.createFormattingModelForPsiFile(
            element.containingFile,
            rootBlock, settings
        )
    }
}
