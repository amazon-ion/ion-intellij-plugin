package com.amazon.ion.plugin.intellij.utils

import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

/**
 * Remove any whitespace nodes from a sequence.
 */
fun Sequence<ASTNode>.filterWhitespace() = filter { !it.isWhitespace() }

/**
 * Whether a node is whitespace.
 */
fun ASTNode.isWhitespace() = elementType == TokenType.WHITE_SPACE

/**
 * Creates a sequence of child nodes from a node.
 */
fun ASTNode.childSequence(): Sequence<ASTNode> =
    if (firstChildNode != null) {
        generateSequence(firstChildNode) { it.treeNext }.filterNotNull()
    } else {
        sequenceOf()
    }

infix fun ASTNode.elementIsA(elementType: IElementType) = this.elementType == elementType

infix fun ASTNode.parentElementIsA(elementType: IElementType) = this.treeParent?.elementType == elementType
