package com.amazon.ion.plugin.intellij.parser

import com.amazon.ion.plugin.intellij.IonParserDefinition
import com.intellij.testFramework.ParsingTestCase
import junit.framework.TestCase
import org.intellij.lang.annotations.Language

abstract class IonParserTestCaseBase(dataPath: String) :
    ParsingTestCase(
        "parser/$dataPath",
        "ion",
        /* lowerCaseFirstLetter */ true,
        IonParserDefinition()
) {
    override fun getTestDataPath(): String = "src/test/resources/test-data"

    fun doTest(@Language("Ion") text: String, expectedTree: String, skipSpaces: Boolean? = null, includeRanges: Boolean? = null) {
        val parsedFile = parseFile("test-input.ion", text.trimIndent())
        val treeText = toParseTreeText(
            parsedFile,
            skipSpaces ?: skipSpaces(),
            includeRanges ?: includeRanges()
        )

        TestCase.assertEquals(expectedTree.trimIndent(), treeText.trim())
    }
}
