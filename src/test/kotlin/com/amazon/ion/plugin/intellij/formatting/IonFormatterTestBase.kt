package com.amazon.ion.plugin.intellij.formatting

import com.intellij.psi.PsiFile
import com.intellij.psi.formatter.FormatterTestCase
import org.intellij.lang.annotations.Language

abstract class IonFormatterTestBase : FormatterTestCase() {
    override fun getTestDataPath(): String = "src/test/resources/test-data"

    override fun getFileExtension(): String = "ion"

    override fun getTestName(lowercaseFirstLetter: Boolean): String =
        super.getTestName(lowercaseFirstLetter).trimIndent()

    override fun doTextTest(
        @Language("Ion") text: String,
        @Language("Ion") textAfter: String
    ) {
        super.doTextTest(text.trimIndent(), textAfter.trimIndent())
    }

    override fun checkDocument(file: PsiFile?, text: String?, textAfter: String?) {
        super.checkDocument(file, text, textAfter)
    }
}
