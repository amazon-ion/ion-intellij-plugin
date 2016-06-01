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

package com.amazon.ion.plugin.intellij.folding;

import com.amazon.ion.plugin.intellij.psi.IonArray;
import com.amazon.ion.plugin.intellij.psi.IonExpression;
import com.amazon.ion.plugin.intellij.psi.IonObject;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IonFoldingBuilder extends FoldingBuilderEx {

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {

        Collection<? extends PsiElement> objects = PsiTreeUtil.findChildrenOfAnyType(root, IonObject.class,
                IonArray.class,
                IonExpression.class);
        if (objects.isEmpty()) {
            return new FoldingDescriptor[0];
        }

        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

        for (PsiElement object : objects) {
            final int startOffset = object.getTextRange().getStartOffset() + 1;
            final int endOffset = object.getTextRange().getEndOffset() - 1;
            if ((startOffset + 1) < endOffset) {
                TextRange range = new TextRange(startOffset, endOffset);
                descriptors.add(new FoldingDescriptor(object.getNode(), range));
            }
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
