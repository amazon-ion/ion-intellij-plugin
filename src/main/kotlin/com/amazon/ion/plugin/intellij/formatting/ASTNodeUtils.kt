package com.amazon.ion.plugin.intellij.formatting

import com.amazon.ion.plugin.intellij.utils.filterWhitespace
import com.intellij.lang.ASTNode
import org.jetbrains.kotlin.idea.base.psi.getLineNumber
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * Determine if a node is on the same line as another node.
 */
fun ASTNode.sameLineAs(another: ASTNode) =
    another.psi.getLineNumber(start = true) == this.psi.getLineNumber(start = true)

/**
 * Return the previous sibling of a node if it exists.
 */
fun ASTNode.previousSibling(): ASTNode? =
    siblings(forward = false).filterWhitespace().firstOrNull()
