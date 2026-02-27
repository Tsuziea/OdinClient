package starred.skies.odin.utils

import com.odtheking.odin.OdinMod.mc
import net.minecraft.client.KeyMapping
import starred.skies.odin.mixin.accessors.MinecraftAccessor

fun leftClick() {
    (mc as MinecraftAccessor).`odinextra$startAttack`()
}

fun rightClick() {
    (mc as MinecraftAccessor).`odinextra$startUseItem`()
}
