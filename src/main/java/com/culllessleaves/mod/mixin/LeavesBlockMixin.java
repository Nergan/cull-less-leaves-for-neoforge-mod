package com.culllessleaves.mod.mixin;

import com.culllessleaves.mod.LeafCulling;
import com.culllessleaves.mod.config.CullSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin implements Cullable {
    @Override
    public boolean cll$shouldCullSide(BlockState state, BlockGetter view, BlockPos pos, Direction facing) {
        return LeafCulling.shouldCullSide(
                CullSettings.INSTANCE.leafDepth(),
                view,
                pos,
                facing,
                CullSettings.INSTANCE.randomRejection(),
                block -> block instanceof LeavesBlock
        );
    }
}
