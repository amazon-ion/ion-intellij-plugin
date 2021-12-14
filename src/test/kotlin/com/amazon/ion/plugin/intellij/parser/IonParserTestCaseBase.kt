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
    private val classLoader = this::class.java.classLoader

    override fun getTestDataPath(): String = "src/test/resources/test-data"

    fun fileScenario(name: String, expected: String? = null) {
        val parsedFile = readClasspathFile("parser/$name.ion")
        val expectedFile = readClasspathFile(expected ?: "parser/$name.expected.txt")

        doTest(parsedFile, expectedFile)
    }

    fun doTest(@Language("Ion") text: String, expectedTree: String, skipSpaces: Boolean? = null, includeRanges: Boolean? = null) {
        val parsedFile = createPsiFile("test-input.ion", text.trimIndent())
        val treeText = toParseTreeText(
            parsedFile,
            skipSpaces ?: skipSpaces(),
            includeRanges ?: includeRanges()
        )

        TestCase.assertEquals(expectedTree.trimIndent(), treeText.trim())
    }

    private fun readClasspathFile(name: String) =
        String(
            requireNotNull(classLoader.getResourceAsStream(name)?.use { it.readAllBytes() }) { "Could not find file $name" },
            Charsets.UTF_8
        )
}
