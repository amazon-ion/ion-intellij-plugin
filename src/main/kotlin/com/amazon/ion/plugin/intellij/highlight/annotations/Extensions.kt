package com.amazon.ion.plugin.intellij.highlight.annotations

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType

/**
 * Infix operator to check if an element has a parent.
 *
 * ex: node parentIsA IonStruct
 */
internal infix fun PsiElement?.parentIsA(type: IElementType) = this?.parent?.elementType == type
