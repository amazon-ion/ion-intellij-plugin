package com.amazon.ion.plugin.intellij.highlight.annotations

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement

interface IonSyntaxAnnotator {
    fun annotate(annotationBuilder: AnnotationBuilder)
}

data class AnnotationBuilder(val element: PsiElement, val holder: AnnotationHolder) {
    fun addSilentTextAttributes(attributes: TextAttributesKey) = apply {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .textAttributes(attributes)
            .create()
    }
}
