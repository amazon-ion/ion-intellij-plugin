// This is a generated file. Not intended for manual editing.
package com.amazon.ion.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface IonContainer extends PsiElement {

  @Nullable
  IonList getList();

  @Nullable
  IonSexpression getSexpression();

  @Nullable
  IonStruct getStruct();

}
