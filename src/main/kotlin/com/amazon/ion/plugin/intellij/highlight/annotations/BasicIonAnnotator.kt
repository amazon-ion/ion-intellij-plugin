package com.amazon.ion.plugin.intellij.highlight.annotations

import com.amazon.ion.plugin.intellij.highlight.IonSyntaxHighlight
import com.amazon.ion.plugin.intellij.psi.IonTypes.ANNOTATION

/**
 * Annotates basic constructs within Ion.
 */
class BasicIonAnnotator : IonSyntaxAnnotator {
    override fun annotate(annotationBuilder: AnnotationBuilder) {
        with (annotationBuilder) {
            when {
                // Annotations include the annotation separator
                element parentIsA ANNOTATION ->
                    addSilentTextAttributes(IonSyntaxHighlight.Attributes.Annotation)

                else -> { /* do nothing */ }
            }
        }
    }
}
