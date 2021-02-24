package com.amazon.ion.plugin.intellij.highlight.annotations

import com.amazon.ion.plugin.intellij.highlight.IonSyntaxHighlight
import com.amazon.ion.plugin.intellij.psi.IonTypes.ANNOTATION
import com.amazon.ion.plugin.intellij.psi.IonTypes.SEXPRESSION_OPERATOR

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

                // Expression operators their parent of their parent is an expression operator
                element.parent parentIsA SEXPRESSION_OPERATOR ->
                    addSilentTextAttributes(IonSyntaxHighlight.Attributes.IonSExpressionOperator)

                else -> { /* do nothing */ }
            }
        }
    }
}
