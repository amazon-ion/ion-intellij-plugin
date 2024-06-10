package com.amazon.ion.plugin.intellij.breadcrumb

import com.amazon.ion.plugin.intellij.IonLanguage
import com.amazon.ion.plugin.intellij.psi.*
import com.intellij.lang.Language
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider

/**
 * Provides breadcrumb information for displaying path to a nested value from the root of a top-level Ion value.
 *
 * Only values inside a container have breadcrumb information.
 * - If value is in a struct, the breadcrumb is the field name. E.g. `foo`
 * - If value is in a sexp, the breadcrumb is the zero-based index of the value, in parentheses. E.g. `(2)`
 * - If value is in a list, the breadcrumb is the zero-based index of the value, in square brackets. E.g. `[2]`
 *
 * What are breadcrumbs? See https://www.jetbrains.com/help/idea/settings-editor-breadcrumbs.html
 */
class IonBreadcrumbsInfoProvider: BreadcrumbsProvider {
    companion object {
        val LANGUAGES: Array<Language> = arrayOf(IonLanguage.INSTANCE)
    }

    override fun getLanguages(): Array<Language> = LANGUAGES

    override fun acceptElement(element: PsiElement): Boolean {
        return element.parent is IonMembers || element.parent is IonListElements || element.parent is IonSexpressionElements
    }

    override fun getElementInfo(element: PsiElement): String {
        return when (val p = element.parent) {
            is IonMembers -> (element as? IonPair)?.key?.text ?: ""
            is IonListElements -> p.valueList.indexOf(element).takeIf { it >= 0 }?.let { "[$it]" } ?: ""
            is IonSexpressionElements -> when (element) {
                is IonSexpressionOperator -> "(0)"
                is IonSexpressionAtom -> {
                    val offset = if (p.sexpressionOperator != null) 1 else 0
                    p.sexpressionAtomList.indexOf(element)
                        .takeIf { it >= 0 }
                        ?.let { "(${it + offset})" }
                        ?: ""
                }
                else -> ""
            }
            else -> TODO("Unreachable")
        }
    }
}