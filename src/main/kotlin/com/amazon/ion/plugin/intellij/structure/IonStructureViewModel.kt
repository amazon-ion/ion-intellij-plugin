package com.amazon.ion.plugin.intellij.structure

import com.amazon.ion.plugin.intellij.psi.IonFile
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

class IonStructureViewModel(psiFile: PsiFile, editor: Editor?) :
    StructureViewModelBase(psiFile, editor, IonStructureViewElement(psiFile)), StructureViewModel.ElementInfoProvider {

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement?): Boolean = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean = element.value is IonFile

    override fun getSorters(): Array<Sorter> = arrayOf(Sorter.ALPHA_SORTER)
}
