package com.amazon.ion.plugin.intellij.highlight

import com.amazon.ion.plugin.intellij.highlight.IonSyntaxHighlight.Attributes
import com.amazon.ion.plugin.intellij.IonIcons.FileType
import com.amazon.ion.plugin.intellij.editor.IonContent
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

/**
 * Extension point which configures a page where color styles can be edited.
 */
class IonColorSettingsPage : ColorSettingsPage {
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = arrayOf(
        AttributesDescriptor("Annotations", Attributes.Annotation),
        AttributesDescriptor("Key", Attributes.Keyword),
        AttributesDescriptor("Separator", Attributes.Separator),
        AttributesDescriptor("Numbers", Attributes.IonNumber),
        AttributesDescriptor("Booleans", Attributes.IonBool),
        AttributesDescriptor("Timestamps", Attributes.IonTimeStamp),
        AttributesDescriptor("Blob", Attributes.IonBlob),
        AttributesDescriptor("Symbols", Attributes.IonSymbol),
        AttributesDescriptor("Strings", Attributes.IonString),
        AttributesDescriptor("Comments", Attributes.Comment),
        AttributesDescriptor("Brackets", Attributes.Brackets),
        AttributesDescriptor("Braces", Attributes.Braces),
        AttributesDescriptor("LOB braces", Attributes.BlobBraces),
        AttributesDescriptor("Parentheses", Attributes.Parenthesis),
        AttributesDescriptor("Operators", Attributes.IonOperators),
        AttributesDescriptor("SExpression operators", Attributes.IonSExpressionOperator)
    )

    override fun getColorDescriptors(): Array<ColorDescriptor> = emptyArray()

    override fun getDisplayName(): String = "Ion"

    override fun getIcon(): Icon = FileType

    override fun getHighlighter(): SyntaxHighlighter = IonSyntaxHighlighter()

    override fun getDemoText(): String = IonContent.SampleIonText

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> = mutableMapOf(
        "expressionOperator" to Attributes.IonSExpressionOperator
    )
}
