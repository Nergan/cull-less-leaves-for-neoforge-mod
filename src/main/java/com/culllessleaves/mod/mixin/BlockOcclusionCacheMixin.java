package com.culllessleaves.mod.mixin;

import com.culllessleaves.mod.config.CullSettings;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Sodium сам решает, рисовать ли грань, и не вызывает {@code Block.shouldRenderFace}.
 * Точка одна и та же в Sodium 0.6.13 и 0.8.13 для NeoForge 1.21.1.
 */
@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache", remap = false)
public class BlockOcclusionCacheMixin {
    @ModifyExpressionValue(
            method = "shouldDrawSide",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;skipRendering(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",
                    remap = true
            )
    )
    private boolean shouldCullSide(boolean isSideInvisible, BlockState state, BlockGetter view, BlockPos pos, Direction facing) {
        if (CullSettings.INSTANCE.enabled() && state.getBlock() instanceof Cullable cullable) {
            return isSideInvisible || cullable.cll$shouldCullSide(state, view, pos, facing);
        }
        return isSideInvisible;
    }
}
