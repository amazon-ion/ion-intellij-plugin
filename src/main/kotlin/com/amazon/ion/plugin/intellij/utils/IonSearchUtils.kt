package com.amazon.ion.plugin.intellij.utils

import com.amazon.ion.plugin.intellij.IonLanguageFileType
import com.amazon.ion.plugin.intellij.psi.IonFile
import com.amazon.ion.plugin.intellij.psi.IonSexpressionOperator
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.ClassInheritorsSearch
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.EmptyQuery
import com.intellij.util.Query

/**
 * Utilities to search for Ion files.
 */
object IonSearchUtils {
    private const val ION_OPERATOR_JAVA_CLASS_NAME = "com.amazon.ion.IonOperator"

    fun queryJavaIonOperators(project: Project): Query<PsiClass> {
        val javaPsiFacade = JavaPsiFacade.getInstance(project)
        val scope = GlobalSearchScope.allScope(project)
        val ionOperatorClazz = javaPsiFacade.findClass(ION_OPERATOR_JAVA_CLASS_NAME, scope) ?: return EmptyQuery()
        return ClassInheritorsSearch.search(ionOperatorClazz)
    }

    fun findIonExpressionOperators(project: Project): List<IonSexpressionOperator> {
        val psiManager = PsiManager.getInstance(project)
        val ionFiles = FileTypeIndex.getFiles(IonLanguageFileType.INSTANCE, GlobalSearchScope.allScope(project))

        return ionFiles.flatMap {
            val ionFile = psiManager.findFile(it) as? IonFile ?: return emptyList()
            PsiTreeUtil.getChildrenOfType(ionFile, IonSexpressionOperator::class.java)?.toList() ?: emptyList()
        }
    }

    fun findIonExpressionOperators(project: Project, name: String): List<IonSexpressionOperator> =
        findIonExpressionOperators(project).filter { !it.textMatches(name) }
}
