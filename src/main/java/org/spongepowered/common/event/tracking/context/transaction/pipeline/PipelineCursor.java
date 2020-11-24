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
package org.spongepowered.common.event.tracking.context.transaction.pipeline;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public final class PipelineCursor {
    public final BlockState state;
    public final int opacity;
    public final BlockPos pos;
    public final @Nullable TileEntity tileEntity;
    public final List<ItemStack> drops;

    public PipelineCursor(final BlockState state, final int opacity, final BlockPos pos,
        @Nullable final TileEntity tileEntity
    ) {
        this.state = state;
        this.opacity = opacity;
        this.pos = pos;
        this.tileEntity = tileEntity;
        this.drops = Collections.emptyList();
    }

    public PipelineCursor(final BlockState state, final int opacity, final BlockPos pos,
        @Nullable final TileEntity tileEntity,
        final List<ItemStack> drops
    ) {
        this.state = state;
        this.opacity = opacity;
        this.pos = pos;
        this.tileEntity = tileEntity;
        this.drops = drops;
    }

    @Override
    public String toString() {
        return new StringJoiner(
            ", ",
            PipelineCursor.class.getSimpleName() + "[",
            "]"
        )
            .add("state=" + this.state)
            .add("opacity=" + this.opacity)
            .add("pos=" + this.pos)
            .add("tileEntity=" + this.tileEntity)
            .add("drops=" + this.drops)
            .toString();
    }
}