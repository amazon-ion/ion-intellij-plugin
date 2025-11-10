package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.IonLanguage
import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider

private const val CODE_STYLE_SETTINGS_DISPLAY_NAME = "Ion"

class IonCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
    override fun getConfigurableDisplayName(): String = CODE_STYLE_SETTINGS_DISPLAY_NAME

    override fun getLanguage() = IonLanguage.INSTANCE

    override fun createConfigurable(settings: CodeStyleSettings, modelSettings: CodeStyleSettings): CodeStyleConfigurable =
        CodeStyleConfigurableConfiguration(settings, modelSettings)
}

private class CodeStyleConfigurableConfiguration(settings: CodeStyleSettings, modelSettings: CodeStyleSettings)
    : CodeStyleAbstractConfigurable(settings, modelSettings, CODE_STYLE_SETTINGS_DISPLAY_NAME) {

    override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel = IonCodeStyleMainPanel(currentSettings, settings)
    override fun getHelpTopic(): String? = null
}

private class IonCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings)
    : TabbedLanguageCodeStylePanel(IonLanguage.INSTANCE, currentSettings, settings) {

    override fun initTabs(settings: CodeStyleSettings?) {
        addIndentOptionsTab(settings)
    }
}
