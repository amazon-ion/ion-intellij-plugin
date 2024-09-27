package com.amazon.ion.plugin.intellij.psi

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.base.psi.getLineCount

/**
 * True if the element is all in a single line.
 *
 * Exists for Backwards Compatibility: <= IC-2020.2
 */
fun PsiElement.isOneLiner() = getLineCount() == 1
