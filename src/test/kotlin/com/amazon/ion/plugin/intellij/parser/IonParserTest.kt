package com.amazon.ion.plugin.intellij.parser

class IonParserTest : IonParserTestCaseBase(dataPath = "parser") {
    fun `test-01-struct`() = fileScenario("01-struct")
    fun `test-02-struct-trailing-comma`() = fileScenario("02-struct-trailing-comma")
    fun `test-03-list`() = fileScenario("03-list")
    fun `test-04-list-trailing-comma`() = fileScenario("04-list-trailing-comma")
    fun `test-05-sexp`() = fileScenario("05-sexp")
    fun `test-06-sexp-operator`() = fileScenario("06-sexp-operator")
    fun `test-07-multiline-string`() = fileScenario("07-multiline-string")
}
