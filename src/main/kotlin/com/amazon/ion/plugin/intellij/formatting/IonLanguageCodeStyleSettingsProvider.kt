package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.IonLanguage
import com.amazon.ion.plugin.intellij.editor.IonContent
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.CommonCodeStyleSettings.IndentOptions
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider.SettingsType.INDENT_SETTINGS

class IonLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    override fun getLanguage(): Language = IonLanguage.INSTANCE

    override fun getCodeSample(settingsType: SettingsType): String = IonContent.SampleIonText

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        if (settingsType == INDENT_SETTINGS) {
            consumer.showAllStandardOptions()
        }
    }

    override fun customizeDefaults(commonSettings: CommonCodeStyleSettings, indentOptions: IndentOptions) {
        // When using spaces or tabs: Use two spaces for indents.
        indentOptions.INDENT_SIZE = 2

        // When using tabs: Treat a tab as two spaces.
        indentOptions.TAB_SIZE = 2

        super.customizeDefaults(commonSettings, indentOptions)
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor = IndentOptionsEditor()
}
