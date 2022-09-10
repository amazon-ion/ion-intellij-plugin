// This is a generated file. Not intended for manual editing.
package com.amazon.ion.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface IonValue extends PsiElement {

  @NotNull
  List<IonAnnotation> getAnnotationList();

  @Nullable
  IonBlob getBlob();

  @Nullable
  IonClob getClob();

  @Nullable
  IonContainer getContainer();

  @Nullable
  IonString getString();

  @Nullable
  IonSymbol getSymbol();

  //WARNING: getValueAsString(...) is skipped
  //matching getValueAsString(IonValue, ...)
  //methods are not found in IonPsiUtil

}
