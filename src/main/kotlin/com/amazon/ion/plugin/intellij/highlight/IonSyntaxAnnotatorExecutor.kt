package com.amazon.ion.plugin.intellij.highlight

import com.amazon.ion.plugin.intellij.highlight.annotations.AnnotationBuilder
import com.amazon.ion.plugin.intellij.highlight.annotations.BasicIonAnnotator
import com.amazon.ion.plugin.intellij.highlight.annotations.BlobClobIonAnnotator
import com.amazon.ion.plugin.intellij.highlight.annotations.IonSyntaxAnnotator
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement

/**
 * Executes all the registered Ion Syntax Annotators.
 */
class IonSyntaxAnnotatorExecutor : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        Annotators.forEach { it.annotate(
            AnnotationBuilder(
                element,
                holder
            )
        ) }
    }
}

/**
 * List of configured {@see IonSyntaxAnnotator}s.
 */
private val Annotators: List<IonSyntaxAnnotator> = listOf(
    BasicIonAnnotator(),
    BlobClobIonAnnotator()
)
