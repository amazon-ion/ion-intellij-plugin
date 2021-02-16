package com.amazon.ion.plugin.intellij.editor.typed

import com.amazon.ion.plugin.intellij.psi.IonFile
import com.amazon.ion.plugin.intellij.psi.IonTypes.LOB_END
import com.amazon.ion.plugin.intellij.psi.IonTypes.RBRACE
import com.amazon.ion.plugin.intellij.psi.IonTypes.RBRACKET
import com.amazon.ion.plugin.intellij.psi.IonTypes.RPAREN
import com.intellij.codeInsight.highlighting.BraceMatchingUtil
import com.intellij.openapi.diagnostic.debug
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.project.Project
import com.intellij.psi.TokenType.BAD_CHARACTER

private val logger = logger<ProcessPairedBracesTypeEventHandler>()

/**
 * Event Handler which processes a missed use case of paired braces.
 *
 * Typically all paired braces are handled through {@see IonCodeBraceMatcher}, however
 * there is a situation where the ordered of brace pairs returned from IonCodeBraceMatcher causes
 * certain brace pairs from being missed due to the brace matching algorithm matching braces outside
 * of the parent braces like this:
 *
 * Bug:
 * {
 *      list: [
 *          { <- start brace
 *      ]
 * } <- end brace
 *
 * This brace matcher will look to see if the currently typed brace has a match within any parent braces.
 */
class ProcessPairedBracesTypeEventHandler : IonTypeEventHandler {
    override fun characterTyped(character: Char, project: Project, editor: EditorEx, file: IonFile) {

        // Check if the character is an open brace, if not then short circuit.
        val bracePair = BracePairs.firstOrNull { character.toString() == it.left } ?: return

        // Create an iterator at the offset and check if we run into a closing brace.
        val iterator = editor.createIteratorAtTyped()
        val offset = editor.caretModel.offset

        // Resolve the brace pair matcher and find the opposite brace type.
        val bracePairMatcher = BraceMatchingUtil.getBraceMatcher(file.fileType, iterator)
        val oppositeBraceType = bracePairMatcher.getOppositeBraceTokenType(iterator.tokenType)

        val firstCloseBracketOrBadCharacter = iterator.sequence().first {
            CloseBraceElements.contains(it) || it == BAD_CHARACTER
        }

        // Check if there is a bad character before we reach the correct closing bracket
        // for the brace pair.
        if (firstCloseBracketOrBadCharacter != oppositeBraceType) {
            logger.debug { "TypeEventHandler '$character': Adding closing bracket '${bracePair.right}'" }
            editor.document.insertString(offset, bracePair.right)
        }
    }
}

private data class BracePair(val left: String, val right: String)

private val BracePairs = listOf(
    BracePair("(", ")"),
    BracePair("{", "}"),
    BracePair("[", "]")
)

private val CloseBraceElements = setOf(RBRACE, RPAREN, RBRACKET, LOB_END)
