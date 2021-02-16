package com.amazon.ion.plugin.intellij.formatting

class IonBasicFormattingTest : IonFormatterTestBase() {
    override fun getBasePath(): String = "formatter"

    fun `test formatter-input-struct`() = doTest()

    fun `test struct with comments`() = doTextTest(
        """
                    {
            // Comments
        another: {
            struct: {
            // Upper struct comment
            memberA: true,
        // Inner struct comment
        memberB: false
            }
        }
        // Around
        }
        """,
        """
        {
            // Comments
            another: {
                struct: {
                    // Upper struct comment
                    memberA: true,
                    // Inner struct comment
                    memberB: false
                }
            }
            // Around
        }
        """
    )

    fun `test struct with comments and expressions`() = doTextTest(
        """
                    {
            // Comments
        another: {
            struct: {
            // Upper struct comment
            memberA: true,
        // Inner struct comment
        memberB: false,
            transform: (join (inner element) { element: "value" })
            }
        }
        // Around
        }
        """,
        """
        {
            // Comments
            another: {
                struct: {
                    // Upper struct comment
                    memberA: true,
                    // Inner struct comment
                    memberB: false,
                    transform: (join
                        (inner
                            element
                        )
                        {
                            element: "value"
                        }
                    )
                }
            }
            // Around
        }
        """
    )

    fun `test expression with comments`() = doTextTest(
        """
            (expression // special case comment inline with expression does not move
            (inner expression) { memberA: true }
            // comment
            value
            // comment struct
            { memberB: true }
            )
        """,
        """
            (expression // special case comment inline with expression does not move
                (inner
                    expression
                )
                {
                    memberA: true
                }
                // comment
                value
                // comment struct
                {
                    memberB: true
                }
            )
        """,
    )

    fun `test tiny expression`() = doTextTest(
        "[]",
        "[]"
    )
}
