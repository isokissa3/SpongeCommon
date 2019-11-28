/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.data.processor.data.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockPlanks;
import net.minecraft.item.ItemStack;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableTreeData;
import org.spongepowered.api.data.manipulator.mutable.block.TreeData;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.common.data.manipulator.mutable.block.SpongeTreeData;
import org.spongepowered.common.data.processor.common.AbstractCatalogDataProcessor;
import org.spongepowered.common.data.value.mutable.SpongeValue;

import java.util.Map;
import java.util.Optional;

public class TreeDataProcessor extends AbstractCatalogDataProcessor<TreeType, Value<TreeType>, TreeData, ImmutableTreeData> {

    private static final Map<ItemType, TreeType> boatMapping = ImmutableMap.<ItemType, TreeType>builder()
            .put(ItemTypes.BOAT, TreeTypes.OAK)
            .put(ItemTypes.ACACIA_BOAT, TreeTypes.ACACIA)
            .put(ItemTypes.BIRCH_BOAT, TreeTypes.BIRCH)
            .put(ItemTypes.DARK_OAK_BOAT, TreeTypes.DARK_OAK)
            .put(ItemTypes.JUNGLE_BOAT, TreeTypes.JUNGLE)
            .put(ItemTypes.SPRUCE_BOAT, TreeTypes.SPRUCE)
            .build();

    public TreeDataProcessor() {
        super(Keys.TREE_TYPE, input -> input.func_77973_b() == ItemTypes.PLANKS || input.func_77973_b() == ItemTypes.LEAVES
                || input.func_77973_b() == ItemTypes.LEAVES2 || input.func_77973_b() == ItemTypes.LOG
                || input.func_77973_b() == ItemTypes.LOG2 || input.func_77973_b() == ItemTypes.SAPLING
                || input.func_77973_b() == ItemTypes.WOODEN_SLAB || boatMapping.containsKey((ItemType) input.func_77973_b()));
    }

    @Override
    protected int setToMeta(TreeType value) {
        return ((BlockPlanks.EnumType) (Object) value).func_176839_a();
    }

    @Override
    protected TreeType getFromMeta(int meta) {
        return (TreeType) (Object) BlockPlanks.EnumType.func_176837_a(meta);
    }

    @Override
    protected Optional<TreeType> getVal(ItemStack stack) {
        if (stack.func_77973_b() == ItemTypes.LEAVES2 || stack.func_77973_b() == ItemTypes.LOG2) {
            return Optional.of(getFromMeta(stack.func_77952_i() + 4));
        } else if (boatMapping.containsKey((ItemType) stack.func_77973_b())) {
            return Optional.of(boatMapping.get(stack.func_77973_b()));
        } else {
            return Optional.of(getFromMeta(stack.func_77952_i()));
        }
    }

    @Override
    public TreeData createManipulator() {
        return new SpongeTreeData();
    }

    @Override
    protected boolean set(ItemStack stack, TreeType value) {
        // TODO - the API needs to be changed, as its no longer possible to change an ItemStack's type

        if (stack.func_77973_b() == ItemTypes.LOG || stack.func_77973_b() == ItemTypes.LEAVES) {
            if (value == TreeTypes.ACACIA || value == TreeTypes.DARK_OAK) {
                return false; // TODO
            }
            stack.func_77964_b(this.setToMeta(value));
            return true;
        }
        else if (stack.func_77973_b() == ItemTypes.LOG2 || stack.func_77973_b() == ItemTypes.LEAVES2) {
            if (value == TreeTypes.OAK || value == TreeTypes.SPRUCE || value == TreeTypes.BIRCH || value == TreeTypes.JUNGLE) {
                return false; // TODO
            }
            stack.func_77964_b(this.setToMeta(value) - 4);
            return true;
        }
        else {
            stack.func_77964_b(this.setToMeta(value));
            return true;
        }
    }

    @Override
    protected TreeType getDefaultValue() {
        return TreeTypes.OAK;
    }

    @Override
    protected Value<TreeType> constructValue(TreeType actualValue) {
        return new SpongeValue<>(this.key, getDefaultValue(), actualValue);
    }

}
