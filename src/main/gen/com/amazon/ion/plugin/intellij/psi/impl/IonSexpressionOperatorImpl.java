// This is a generated file. Not intended for manual editing.
package com.amazon.ion.plugin.intellij.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.amazon.ion.plugin.intellij.psi.IonTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.amazon.ion.plugin.intellij.psi.*;

public class IonSexpressionOperatorImpl extends ASTWrapperPsiElement implements IonSexpressionOperator {

  public IonSexpressionOperatorImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IonVisitor visitor) {
    visitor.visitSexpressionOperator(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IonVisitor) accept((IonVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<IonAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, IonAnnotation.class);
  }

  @Override
  @NotNull
  public IonSymbol getSymbol() {
    return findNotNullChildByClass(IonSymbol.class);
  }

}
