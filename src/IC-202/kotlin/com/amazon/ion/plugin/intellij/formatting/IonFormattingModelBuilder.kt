package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.formatting.blocks.IonBlockOptions
import com.amazon.ion.plugin.intellij.formatting.blocks.RootIonBlock
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleSettings

/**
 * Creates the block model for an Ion file.
 *
 * The block model will determine how elements are spaced, indented and aligned.
 */
class IonFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
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
