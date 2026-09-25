package com.culllessleaves.mod.mixin;

import com.culllessleaves.mod.LeafCulling;
import com.culllessleaves.mod.config.CullSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MangroveRootsBlock.class)
public class MangroveRootsBlockMixin implements Cullable {
    @Override
    public boolean cll$shouldCullSide(BlockState state, BlockGetter view, BlockPos pos, Direction facing) {
        if (!CullSettings.INSTANCE.fastMangroveRoots()) {
            return false;
        }
        return LeafCulling.shouldCullSide(
                1,
                view,
                pos,
                facing,
                CullSettings.INSTANCE.randomRejection(),
                block -> block instanceof MangroveRootsBlock
        );
    }
}
