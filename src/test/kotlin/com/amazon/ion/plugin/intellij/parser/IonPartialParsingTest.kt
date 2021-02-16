package com.amazon.ion.plugin.intellij.parser

/**
 * Partially complete Ion Partial expressions.
 */
class IonPartialParsingTest : IonParserTestCaseBase(dataPath = "partial") {
    fun `test partial list`() = doTest(
        text = " [ element another ] ",
        expectedTree = """
            Ion File
              IonValueImpl(VALUE)
                IonObjectImpl(OBJECT)
                  IonArrayImpl(ARRAY)
                    PsiElement(LBRACKET)('[')
                    PsiWhiteSpace(' ')
                    IonElementsImpl(ELEMENTS)
                      IonValueImpl(VALUE)
                        IonSymbolImpl(SYMBOL)
                          PsiElement(IDENTIFIER)('element')
                      PsiErrorElement:ANNOTATION_SEPARATOR, COMMA or RBRACKET expected, got 'another'
                        <empty list>
                      PsiWhiteSpace(' ')
                      IonValueImpl(VALUE)
                        IonSymbolImpl(SYMBOL)
                          PsiElement(IDENTIFIER)('another')
                    PsiWhiteSpace(' ')
                    PsiElement(RBRACKET)(']')
              PsiWhiteSpace(' ')
        """
    )

    fun `test partial struct`() = doTest(
        text = " { member: true memberB: true } ",
        expectedTree = """
            Ion File
              IonValueImpl(VALUE)
                IonObjectImpl(OBJECT)
                  IonStructImpl(STRUCT)
                    PsiElement(LBRACE)('{')
                    PsiWhiteSpace(' ')
                    IonMembersImpl(MEMBERS)
                      IonPairImpl(PAIR)
                        IonKeyImpl(KEY)
                          PsiElement(KEY_NAME)('member')
                        PsiElement(SEPARATOR)(':')
                        PsiWhiteSpace(' ')
                        IonValueImpl(VALUE)
                          PsiElement(BOOLEAN)('true')
                      PsiErrorElement:COMMA or RBRACE expected, got 'memberB'
                        <empty list>
                      PsiWhiteSpace(' ')
                      IonPairImpl(PAIR)
                        IonKeyImpl(KEY)
                          PsiElement(KEY_NAME)('memberB')
                        PsiElement(SEPARATOR)(':')
                        PsiWhiteSpace(' ')
                        IonValueImpl(VALUE)
                          PsiElement(BOOLEAN)('true')
                    PsiWhiteSpace(' ')
                    PsiElement(RBRACE)('}')
              PsiWhiteSpace(' ')
        """
    )

    /**
     * In the blob case, we parse a blob with an invalid inner value
     * and flag it later in annotations.
     */
    fun `test blob recover`() = doTest(
        text = "( {{ abcde_not_valid_base64 }} )",
        expectedTree = """
        Ion File
          IonValueImpl(VALUE)
            IonObjectImpl(OBJECT)
              IonExpressionImpl(EXPRESSION)
                PsiElement(LPAREN)('(')
                PsiWhiteSpace(' ')
                IonAtomsImpl(ATOMS)
                  IonValueImpl(VALUE)
                    IonBlobImpl(BLOB)
                      PsiElement(LOB_START)('{{')
                      PsiWhiteSpace(' ')
                      PsiElement(BAD_CHARACTER)('abcde_not_valid_base64')
                      PsiWhiteSpace(' ')
                      PsiElement(LOB_END)('}}')
                PsiWhiteSpace(' ')
                PsiElement(RPAREN)(')')
        """
    )
}
