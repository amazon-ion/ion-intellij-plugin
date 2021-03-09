package com.amazon.ion.plugin.intellij.folding

import com.amazon.ion.plugin.intellij.psi.IonContainer
import com.amazon.ion.plugin.intellij.psi.IonList
import com.amazon.ion.plugin.intellij.psi.IonSexpression
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

/**
 * Configures how an Ion file should be folded:
 *
 * i.e. the + button on the side.
 */
class IonFoldingBuilder : FoldingBuilderEx() {
    override fun buildFoldRegions(
        root: PsiElement,
        document: Document,
        quick: Boolean
    ): Array<FoldingDescriptor> =
        PsiTreeUtil.findChildrenOfAnyType(
            root,
            IonContainer::class.java,
            IonList::class.java,
            IonSexpression::class.java
        )
        .mapNotNull {
            val startOffset = it.textRange.startOffset + 1
            val endOffset = it.textRange.endOffset - 1
            if (startOffset + 1 < endOffset) {
                FoldingDescriptor(
                    it.node,
                    TextRange(startOffset, endOffset)
                )
            } else {
                 null
            }
        }.toTypedArray()

    override fun getPlaceholderText(node: ASTNode): String = "..."

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
