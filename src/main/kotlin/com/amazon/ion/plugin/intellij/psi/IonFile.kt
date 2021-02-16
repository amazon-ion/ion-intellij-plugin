package com.amazon.ion.plugin.intellij.psi

import com.amazon.ion.plugin.intellij.IonLanguage
import com.amazon.ion.plugin.intellij.IonLanguageFileType
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class IonFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, IonLanguage.INSTANCE) {

    override fun getFileType(): FileType = IonLanguageFileType.INSTANCE

    override fun toString(): String = "Ion File"
}
