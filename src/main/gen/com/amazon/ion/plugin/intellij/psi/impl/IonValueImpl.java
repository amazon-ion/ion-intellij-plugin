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

public class IonValueImpl extends ASTWrapperPsiElement implements IonValue {

  public IonValueImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IonVisitor visitor) {
    visitor.visitValue(this);
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
  @Nullable
  public IonBlob getBlob() {
    return findChildByClass(IonBlob.class);
  }

  @Override
  @Nullable
  public IonClob getClob() {
    return findChildByClass(IonClob.class);
  }

  @Override
  @Nullable
  public IonObject getObject() {
    return findChildByClass(IonObject.class);
  }

  @Override
  @Nullable
  public IonString getString() {
    return findChildByClass(IonString.class);
  }

  @Override
  @Nullable
  public IonSymbol getSymbol() {
    return findChildByClass(IonSymbol.class);
  }

  @Override
  @Nullable
  public String getValueAsString() {
    return IonPsiUtil.getValueAsString(this);
  }

}
