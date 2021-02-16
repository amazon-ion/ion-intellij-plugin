package com.amazon.ion.plugin.intellij.editor.typed

import com.amazon.ion.plugin.intellij.psi.IonFile
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.project.Project

/**
 * Type event handler within an Ion File.
 */
interface IonTypeEventHandler {
    fun characterTyped(character: Char, project: Project, editor: EditorEx, file: IonFile)
}
