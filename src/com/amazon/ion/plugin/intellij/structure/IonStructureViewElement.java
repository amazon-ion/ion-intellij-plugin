/*
 * Copyright 2015-[2016] Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.amazon.ion.plugin.intellij.structure;

import com.amazon.ion.plugin.intellij.IonIcons;
import com.amazon.ion.plugin.intellij.psi.*;
import com.amazon.ion.plugin.intellij.psi.impl.IonPsiImplUtil;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Structural representation for an Ion element
 */
public class IonStructureViewElement implements StructureViewTreeElement, SortableTreeElement {

    private PsiElement element;

    public IonStructureViewElement(PsiElement element) {
        this.element = element;
    }

    @Override
    public Object getValue() {
        return this.element;
    }

    @Override
    public void navigate(boolean requestFocus) {
        if (element instanceof NavigationItem) {
            ((NavigationItem) element).navigate(requestFocus);
        }
    }

    @Override
    public boolean canNavigate() {
        return element instanceof NavigationItem &&
                ((NavigationItem) element).canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return element instanceof NavigationItem &&
                ((NavigationItem) element).canNavigateToSource();
    }

    @NotNull
    @SuppressWarnings("ConstantConditions")
    @Override
    public String getAlphaSortKey() {
        return element instanceof IonPair ? ((IonPair) element).getKey().getKeyName() : element.getText();
    }

    @NotNull
    @SuppressWarnings("ConstantConditions")
    @Override
    public ItemPresentation getPresentation() {

        return new ItemPresentation() {

            @Nullable
            @Override
            public String getPresentableText() {

                if (element instanceof IonPair) {
                    return ((IonPair) element).getKey().getKeyName();
                } else if (element instanceof IonStruct) {
                    return "{...}";
                } else if (element instanceof IonArray) {
                    return "[...]";
                } else if (element instanceof IonExpression) {
                    return "(...)";
                } else if (element instanceof IonValue) {
                    return ((IonValue) element).getValueAsString();
                } else if (element instanceof IonFile) {
                    return ((IonFile) element).getVirtualFile().getPresentableName();
                }
                return element.getClass().getSimpleName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getTextRange().getStartOffset() + ".." + element.getTextRange().getEndOffset();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return IonIcons.FILE;
            }
        };
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @NotNull
    @Override
    public TreeElement[] getChildren() {

        List<TreeElement> treeElements = new ArrayList<TreeElement>();

        if (element instanceof IonFile) {
            IonValue[] topChildren = PsiTreeUtil.getChildrenOfType(element, IonValue.class);
            if (topChildren != null) {
                for (IonValue child : topChildren) {
                    treeElements.add(new IonStructureViewElement(child));
                }
            }
        } else if (element instanceof IonStruct) {

            IonStruct struct = (IonStruct) element;
            IonMembers members = struct.getMembers();
            if (members != null) {
                List<IonPair> pairs = members.getPairList();
                for (IonPair pair : pairs) {
                    treeElements.add(new IonStructureViewElement(pair));
                }
            }
        } else if (element instanceof IonArray) {

            IonElements elements = ((IonArray) element).getElements();
            if (elements != null) {
                addValues(treeElements, elements.getValueList());
            }

        } else if (element instanceof IonExpression) {
            IonAtoms atoms = ((IonExpression) element).getAtoms();
            if (atoms != null) {
                addValues(treeElements, atoms.getValueList());
            }
        } else if (element instanceof IonPair) {

            IonValue val = ((IonPair) element).getValue();
            IonObject json = val.getObject();
            if (json != null) {
                treeElements.add(new IonStructureViewElement(json.getStruct()));
            } else {
                treeElements.add(new IonStructureViewElement(((IonPair) element).getValue()));
            }

        } else if (element instanceof IonValue) {
            IonValue val = (IonValue) element;
            IonObject object = val.getObject();
            if (object != null) {
                treeElements.add(new IonStructureViewElement(IonPsiImplUtil.getPsiElement(object)));
            }
        }

        return treeElements.toArray(new IonStructureViewElement[treeElements.size()]);
    }

    private void addValues(List<TreeElement> treeElements, List<IonValue> valueList) {
        for (IonValue value : valueList) {
            IonObject object = value.getObject();
            if (object != null) {
                treeElements.add(new IonStructureViewElement(IonPsiImplUtil.getPsiElement(object)));
            } else {
                treeElements.add(new IonStructureViewElement(value));
            }
        }
    }
}
