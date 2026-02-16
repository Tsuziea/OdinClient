package starred.skies.odin.mixin.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import starred.skies.odin.features.impl.cheats.SecretHitboxes;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @WrapOperation(method = "extractBlockOutline(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/state/LevelRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape extractBlockOutline(BlockState instance, BlockGetter level, BlockPos pos, CollisionContext context, Operation<VoxelShape> original) {
        VoxelShape outline = SecretHitboxes.INSTANCE.getShape(instance);
        return outline != null ? outline : original.call(instance, level, pos, context);
    }
}