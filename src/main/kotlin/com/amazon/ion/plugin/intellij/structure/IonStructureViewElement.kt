package com.amazon.ion.plugin.intellij.structure

import com.amazon.ion.plugin.intellij.IonIcons
import com.amazon.ion.plugin.intellij.psi.IonFile
import com.amazon.ion.plugin.intellij.psi.IonList
import com.amazon.ion.plugin.intellij.psi.IonPair
import com.amazon.ion.plugin.intellij.psi.IonSexpression
import com.amazon.ion.plugin.intellij.psi.IonStruct
import com.amazon.ion.plugin.intellij.psi.IonValue
import com.amazon.ion.plugin.intellij.psi.impl.IonPsiUtil
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import javax.swing.Icon

class IonStructureViewElement(private val element: PsiElement) : StructureViewTreeElement, SortableTreeElement {
    override fun getPresentation(): ItemPresentation =
        object : ItemPresentation {
            override fun getPresentableText(): String? =
                when (element) {
                    is IonPair -> element.key.keyName
                    is IonStruct -> "{...}"
                    is IonList -> "[...]"
                    is IonSexpression -> "(...)"
                    is IonValue -> element.valueAsString
                    is IonFile -> element.virtualFile.presentableName
                    else -> element::class.java.simpleName
                }

            override fun getLocationString(): String =
                element.textRange.startOffset.toString() + ".." + element.textRange.endOffset.toString()

            override fun getIcon(unused: Boolean): Icon = IonIcons.FileType
        }

    override fun getChildren(): Array<IonStructureViewElement> =
        when (element) {
            is IonFile -> PsiTreeUtil.getChildrenOfType(element, IonValue::class.java)?.map { IonStructureViewElement(it) }?.toTypedArray()

            is IonStruct -> element.members?.pairList?.map { IonStructureViewElement(it) }?.toTypedArray()

            is IonSexpression -> listOfNotNull(
                listOfNotNull(element.sexpressionOperator?.let { IonStructureViewElement(it) }),
                element.atoms?.valueList?.getViewElementChildren()
            ).flatten().toTypedArray()

            is IonPair -> arrayOf(element.value.container?.struct?.let { IonStructureViewElement(it) } ?: IonStructureViewElement(element.value))

            is IonValue -> listOfNotNull(element.container?.let { IonStructureViewElement(IonPsiUtil.getPsiElement(it)!!) }).toTypedArray()

            else -> emptyArray()
        } ?: emptyArray()

    override fun navigate(requestFocus: Boolean) {
        if (element is NavigationItem) {
            element.navigate(requestFocus)
        }
    }

    override fun canNavigate(): Boolean =
        if (element is NavigationItem) {
            element.canNavigate()
        } else {
            false
        }

    override fun canNavigateToSource(): Boolean =
        if (element is NavigationItem) {
            element.canNavigateToSource()
        } else {
            false
        }

    override fun getValue(): Any = element

    override fun getAlphaSortKey(): String = (element as? IonPair)?.key?.keyName ?: element.text

    private fun List<IonValue>.getViewElementChildren(): List<IonStructureViewElement> =
        map { value ->
            value.container?.let { IonStructureViewElement(IonPsiUtil.getPsiElement(it)!!) }
                ?: IonStructureViewElement(value)
        }
}
