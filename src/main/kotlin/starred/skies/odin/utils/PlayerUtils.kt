package starred.skies.odin.utils

import com.odtheking.odin.OdinMod.mc
import net.minecraft.client.KeyMapping
import starred.skies.odin.mixin.accessors.MinecraftAccessor

fun leftClick() {
    (mc as MinecraftAccessor).`odinclient$continueAttack`(false) //clear missTime to avoid cooldown.
    (mc as MinecraftAccessor).`odinclient$startAttack`()
}

fun rightClick() {
    (mc as MinecraftAccessor).`odinclient$startUseItem`()
}
