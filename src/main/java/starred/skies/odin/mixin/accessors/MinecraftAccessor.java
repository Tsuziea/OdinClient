package starred.skies.odin.mixin.accessors;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
@SuppressWarnings("unused")
public interface MinecraftAccessor {
    @Invoker("startAttack")
    boolean odinextra$startAttack();

    @Invoker("continueAttack")
    void odinextra$continueAttack(boolean isHoldingAttack);

    @Invoker("startUseItem")
    void odinextra$startUseItem();
}
