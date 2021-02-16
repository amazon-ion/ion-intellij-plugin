package com.amazon.ion.plugin.intellij.highlight

import com.amazon.ion.plugin.intellij.highlight.IonSyntaxHighlight.Attributes
import com.amazon.ion.plugin.intellij.IonIcons.FileType
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
        AttributesDescriptor("Expression operators", Attributes.IonExpressionOperators)
    )

    override fun getColorDescriptors(): Array<ColorDescriptor> = emptyArray()

    override fun getDisplayName(): String = "Ion"

    override fun getIcon(): Icon = FileType

    override fun getHighlighter(): SyntaxHighlighter = IonSyntaxHighlighter()

    override fun getDemoText(): String = """
        // Demo text for ion colors
        'Annotation'::AnotherAnnotation::{
            float_key: 2.55, /* This is an inline comment */,
            boolean_key: false,
            identifier_key: "ABC-123",
            integer_key: 55,
            symbol_key: 'Quoted Symbol',
            other_symbol: NonQuotedSymbol,
            blob_key: {{ VG8gaW5maW5pdHkuLi4gYW5kIGJleW9uZCE= }},
            expression: (<expressionOperator>and</expressionOperator>
                (<expressionOperator>equals</expressionOperator> 1 1)
                (<expressionOperator>greaterThan</expressionOperator> 2 1)
            ),
            timestamp_key: 2007-22-23T20:14:33Z,
            array_key: [ "Note A", "Note B" ],
            other_numbers: [ 0x10FBAD, 0b110101 ],
            struct_key: {
                integer: 25
                string: "Simple struct"
            }
        }
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> = mutableMapOf(
        "expressionOperator" to Attributes.IonExpressionOperators
    )
}
