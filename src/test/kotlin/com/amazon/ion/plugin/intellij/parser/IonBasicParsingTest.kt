package com.amazon.ion.plugin.intellij.parser

/**
 * Parsing tests for basic structures.
 */
class IonBasicParsingTest : IonParserTestCaseBase(dataPath = "basic") {
    fun `test struct`() = doTest(
        """
            { key: value }
        """,
        """
            Ion File
              IonValueImpl(VALUE)
                IonContainerImpl(CONTAINER)
                  IonStructImpl(STRUCT)
                    PsiElement(LBRACE)('{')
                    PsiWhiteSpace(' ')
                    IonMembersImpl(MEMBERS)
                      IonPairImpl(PAIR)
                        IonKeyImpl(KEY)
                          PsiElement(KEY_NAME)('key')
                        PsiElement(SEPARATOR)(':')
                        PsiWhiteSpace(' ')
                        IonValueImpl(VALUE)
                          IonSymbolImpl(SYMBOL)
                            PsiElement(IDENTIFIER)('value')
                    PsiWhiteSpace(' ')
                    PsiElement(RBRACE)('}')
        """
    )

    fun `test struct with trailing comma`() = doTest(
        """
            { key: value, }
        """,
        """
            Ion File
              IonValueImpl(VALUE)
                IonContainerImpl(CONTAINER)
                  IonStructImpl(STRUCT)
                    PsiElement(LBRACE)('{')
                    PsiWhiteSpace(' ')
                    IonMembersImpl(MEMBERS)
                      IonPairImpl(PAIR)
                        IonKeyImpl(KEY)
                          PsiElement(KEY_NAME)('key')
                        PsiElement(SEPARATOR)(':')
                        PsiWhiteSpace(' ')
                        IonValueImpl(VALUE)
                          IonSymbolImpl(SYMBOL)
                            PsiElement(IDENTIFIER)('value')
                      PsiElement(COMMA)(',')
                    PsiWhiteSpace(' ')
                    PsiElement(RBRACE)('}')
        """
    )

    fun `test list`() = doTest(
        """
            [ key ]
        """,
        """
            Ion File
              IonValueImpl(VALUE)
                IonContainerImpl(CONTAINER)
                  IonListImpl(LIST)
                    PsiElement(LBRACKET)('[')
                    PsiWhiteSpace(' ')
                    IonElementsImpl(ELEMENTS)
                      IonValueImpl(VALUE)
                        IonSymbolImpl(SYMBOL)
                          PsiElement(IDENTIFIER)('key')
                    PsiWhiteSpace(' ')
                    PsiElement(RBRACKET)(']')
        """
    )

    fun `test list with trailing comma`() = doTest(
        """
            [ key, ]
        """,
        """
            Ion File
              IonValueImpl(VALUE)
                IonContainerImpl(CONTAINER)
                  IonListImpl(LIST)
                    PsiElement(LBRACKET)('[')
                    PsiWhiteSpace(' ')
                    IonElementsImpl(ELEMENTS)
                      IonValueImpl(VALUE)
                        IonSymbolImpl(SYMBOL)
                          PsiElement(IDENTIFIER)('key')
                      PsiElement(COMMA)(',')
                    PsiWhiteSpace(' ')
                    PsiElement(RBRACKET)(']')
        """
    )

    fun `test sexp`() = doTest(
        """
            ("first" "second" {})
        """,
        """
        Ion File
          IonValueImpl(VALUE)
            IonContainerImpl(CONTAINER)
              IonSexpressionImpl(SEXPRESSION)
                PsiElement(LPAREN)('(')
                IonAtomsImpl(ATOMS)
                  IonValueImpl(VALUE)
                    IonStringImpl(STRING)
                      IonQqStringImpl(QQ_STRING)
                        PsiElement(QQ_START)('"')
                        PsiElement(QQ_VALUE)('first')
                        PsiElement(QQ_END)('"')
                  PsiWhiteSpace(' ')
                  IonValueImpl(VALUE)
                    IonStringImpl(STRING)
                      IonQqStringImpl(QQ_STRING)
                        PsiElement(QQ_START)('"')
                        PsiElement(QQ_VALUE)('second')
                        PsiElement(QQ_END)('"')
                  PsiWhiteSpace(' ')
                  IonValueImpl(VALUE)
                    IonContainerImpl(CONTAINER)
                      IonStructImpl(STRUCT)
                        PsiElement(LBRACE)('{')
                        IonMembersImpl(MEMBERS)
                          <empty list>
                        PsiElement(RBRACE)('}')
                PsiElement(RPAREN)(')')
        """
    )

    fun `test sexp with SEXPRESSION operator`() = doTest(
        """
            (test element)
        """,
        """
            Ion File
              IonValueImpl(VALUE)
                IonContainerImpl(CONTAINER)
                  IonSexpressionImpl(SEXPRESSION)
                    PsiElement(LPAREN)('(')
                    IonSexpressionOperatorImpl(SEXPRESSION_OPERATOR)
                      IonSymbolImpl(SYMBOL)
                        PsiElement(IDENTIFIER)('test')
                    PsiWhiteSpace(' ')
                    IonAtomsImpl(ATOMS)
                      IonValueImpl(VALUE)
                        IonSymbolImpl(SYMBOL)
                          PsiElement(IDENTIFIER)('element')
                    PsiElement(RPAREN)(')')
        """
    )

    fun `test multiline qqq string`() = doTest(
        """
            (
                test
                '''hello'''
                '''another'''
            )
        """,
        """
            Ion File
              IonValueImpl(VALUE)
                IonContainerImpl(CONTAINER)
                  IonSexpressionImpl(SEXPRESSION)
                    PsiElement(LPAREN)('(')
                    IonSexpressionOperatorImpl(SEXPRESSION_OPERATOR)
                      IonSymbolImpl(SYMBOL)
                        PsiElement(IDENTIFIER)('test')
                    PsiWhiteSpace(' ')
                    IonAtomsImpl(ATOMS)
                      IonValueImpl(VALUE)
                        IonSymbolImpl(SYMBOL)
                          PsiElement(IDENTIFIER)('element')
                    PsiElement(RPAREN)(')')
        """
    )
}
