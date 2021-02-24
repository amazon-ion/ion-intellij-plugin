package com.amazon.ion.plugin.intellij.highlight.annotations

/**
 * Identifies issues with Ion Blob and Ion Clobs.
 *
 * TODO: Validate clob values as per Ion Grammar guide http://amzn.github.io/ion-docs/docs/stringclob.html
 */
class BlobClobIonAnnotator : IonSyntaxAnnotator {
    override fun annotate(annotationBuilder: AnnotationBuilder) {
        with (annotationBuilder) {
            when {
                else -> { /* do nothing */ }
            }
        }
    }
}
