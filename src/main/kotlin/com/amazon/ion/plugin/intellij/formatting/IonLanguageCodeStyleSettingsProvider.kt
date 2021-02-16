package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.IonLanguage
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.CommonCodeStyleSettings.IndentOptions
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider.SettingsType.INDENT_SETTINGS

class IonLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    override fun getLanguage(): Language = IonLanguage.INSTANCE

    override fun getCodeSample(settingsType: SettingsType): String =
        """
        // Demo text for code style.
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

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        if (settingsType == INDENT_SETTINGS) {
            consumer.showAllStandardOptions()
        }
    }

    override fun customizeDefaults(commonSettings: CommonCodeStyleSettings, indentOptions: IndentOptions) {
        super.customizeDefaults(commonSettings, indentOptions)
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor = IndentOptionsEditor()
}
