package com.amazon.ion.plugin.intellij.editor

import com.amazon.ion.plugin.intellij.editor.typed.ProcessPairedBracesTypeEventHandler
import com.amazon.ion.plugin.intellij.psi.IonFile
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate.Result.CONTINUE
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * Handles a type event within an Ion File Type.
 */
class IonTypedHandler : TypedHandlerDelegate() {
    companion object {
        private val Handlers = listOf(
            ProcessPairedBracesTypeEventHandler()
        )
    }
    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result =
        CONTINUE.apply {
            editor as? EditorEx ?: error("Editor was not the extended editor, not expected.")

            if (file is IonFile) {
                Handlers.forEach { it.characterTyped(c, project, editor, file) }
            }
        }
}
