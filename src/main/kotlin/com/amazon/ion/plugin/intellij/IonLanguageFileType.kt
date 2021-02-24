package com.amazon.ion.plugin.intellij

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class IonLanguageFileType : LanguageFileType(IonLanguage.INSTANCE) {
    companion object {
        @JvmStatic
        val INSTANCE = IonLanguageFileType()
    }

    override fun getName(): String = "Ion"

    override fun getDescription(): String = "Ion language file"

    override fun getDefaultExtension(): String = "ion"

    override fun getIcon(): Icon = IonIcons.FileType
}
