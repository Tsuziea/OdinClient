package starred.skies.odin.utils

import com.odtheking.odin.OdinMod.mc
import starred.skies.odin.mixin.accessors.KeyMappingAccessor

fun rightClick() {
    val options = mc.options ?: return
    (options.keyUse as KeyMappingAccessor).clickCount += 1
}

fun leftClick() {
    val options = mc.options ?: return
    (options.keyAttack as KeyMappingAccessor).clickCount += 1
}