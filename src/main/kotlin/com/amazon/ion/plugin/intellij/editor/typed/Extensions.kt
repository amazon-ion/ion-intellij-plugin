package com.amazon.ion.plugin.intellij.editor.typed

import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.HighlighterIterator

/**
 * Creates an iterator at the current caret offset.
 *
 * Note: This won't include the currently typed character as that is behind the caret.
 */
internal fun EditorEx.createIteratorAtCaret() = highlighter.createIterator(caretModel.offset)

/**
 * Creates an iterator which is retreated by a single token. This is effectively
 * an iterator which starts at the typed character.
 *
 * Note: This is effectively one character behind the caret, otherwise known as the currently
 * typed character.
 */
internal fun EditorEx.createIteratorAtTyped() = createIteratorAtCaret().apply {
    val caretOffset = caretModel.offset
    val documentLength = document.textLength
    val atDocumentEnd = caretOffset == documentLength

    if (!atDocumentEnd) {
        retreat()
    }
}

/**
 * Creates a sequence from a highlighter iterator.
 *
 * Note: May not be safe to re-trigger, use once and then dispose of the iterator.
 */
internal fun HighlighterIterator.sequence() =
    generateSequence(tokenType) {
        advance()
        if (!atEnd()) {
            tokenType
        } else {
            null
        }
    }
