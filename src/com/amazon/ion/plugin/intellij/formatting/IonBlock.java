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

package com.amazon.ion.plugin.intellij.formatting;

import com.amazon.ion.plugin.intellij.psi.IonTypes;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.amazon.ion.plugin.intellij.psi.IonTypes.*;

/**
 * A block of ion text for formatting
 * NOTE: Incomplete and does not work
 */
public class IonBlock extends AbstractBlock {

    private final CodeStyleSettings settings;
    private SpacingBuilder spacingBuilder;

    public static final ArrayList<IElementType> pairContainers = new ArrayList<IElementType>(Collections.singletonList(MEMBERS));
    public static final ArrayList<IElementType> arrayContainers = new ArrayList<IElementType>(Collections.singletonList(ELEMENTS));
    public static final ArrayList<IElementType> expressionContainers = new ArrayList<IElementType>(Collections.singletonList(ATOMS));


    protected IonBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, SpacingBuilder spacingBuilder, CodeStyleSettings settings) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
        this.settings = settings;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<Block>();
        if (myNode.getElementType() == STRUCT) {
            goDeep(blocks, myNode, myAlignment, Alignment.createAlignment(), false, pairContainers);
        } else if (myNode.getElementType() == ARRAY) {
            goDeep(blocks, myNode, myAlignment, Alignment.createAlignment(), false, arrayContainers);
        } else if (myNode.getElementType() == EXPRESSION) {
            goDeep(blocks, myNode, myAlignment, Alignment.createAlignment(), false, expressionContainers);
        } else {

            ASTNode child = myNode.getFirstChildNode();
            while (child != null) {
                if (child.getElementType() != TokenType.WHITE_SPACE) {
                    Block block = new IonBlock(child, Wrap.createWrap(WrapType.NONE, false), myAlignment, spacingBuilder, settings);
                    blocks.add(block);
                }
                child = child.getTreeNext();
            }
        }
        return blocks;
    }

    private void goDeep(List<Block> blocks, ASTNode node, Alignment baseAlignment, Alignment childAlignment, boolean useChildAlignmentForBlocks, List<IElementType> childContainerTypes) {

        Alignment al = useChildAlignmentForBlocks ? childAlignment : baseAlignment;
        ASTNode cnode = node.getFirstChildNode();

        while (cnode != null) {
            if (childContainerTypes.contains(cnode.getElementType())) {
                goDeep(blocks, cnode, baseAlignment, childAlignment, true, childContainerTypes);
            } else if (cnode.getElementType() != TokenType.WHITE_SPACE) {
                blocks.add(new IonBlock(cnode, Wrap.createWrap(WrapType.NORMAL, false), al, spacingBuilder, settings));
            }
            cnode = cnode.getTreeNext();
        }

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Indent getIndent() {

        if (myNode.getElementType() == IonTypes.PAIR ||
                (myNode.getElementType() == VALUE && myNode.getTreeParent().getElementType() == ELEMENTS) ||
                ((myNode.getElementType() == VALUE || myNode.getElementType() == OPERATOR) && myNode.getTreeParent().getElementType() == ATOMS)) {
            return Indent.getSpaceIndent(this.settings.getIndentOptions().INDENT_SIZE);
        } else {
            return Indent.getNoneIndent();
        }
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
