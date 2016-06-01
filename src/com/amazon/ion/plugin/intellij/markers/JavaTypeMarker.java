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

package com.amazon.ion.plugin.intellij.markers;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;

import static com.amazon.ion.plugin.intellij.psi.IonTypes.*;

/**
 * Generates a marker in the gutter for recognized classes.
 */
public class JavaTypeMarker extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {

        final Project project = element.getProject();
        GlobalSearchScope searchScope = GlobalSearchScope.allScope(project);

        final IElementType elementType = element.getNode().getElementType();
        if (elementType == QQ_VALUE || elementType == Q_VALUE || elementType == IDENTIFIER) {
            markClassIfValid(element, result, project, searchScope, element.getText());
        }

    }

    private void markClassIfValid(PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result,
                                  Project project, GlobalSearchScope searchScope, String className) {

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, searchScope);

        if (psiClass != null) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(getIconForValue(psiClass)).
                            setTargets(psiClass).
                            setTooltipText("Navigate to " + className);
            result.add(builder.createLineMarkerInfo(element));
        }
    }

    private Icon getIconForValue(final PsiClass psiClass) {
        if (psiClass.isAnnotationType()) {
            return AllIcons.Nodes.Annotationtype;
        } else if (psiClass.isEnum()) {
            return AllIcons.Nodes.Enum;
        } else if (psiClass.isInterface()) {
            return AllIcons.Nodes.Interface;
        }

        return AllIcons.Nodes.Class;
    }
}
