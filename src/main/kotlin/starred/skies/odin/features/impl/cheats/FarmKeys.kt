package starred.skies.odin.features.impl.cheats

import com.mojang.blaze3d.platform.InputConstants
import com.odtheking.odin.clickgui.settings.impl.BooleanSetting
import com.odtheking.odin.clickgui.settings.impl.KeybindSetting
import com.odtheking.odin.features.Module
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW
import starred.skies.odin.mixin.accessors.KeyMappingAccessor
import starred.skies.odin.utils.Skit

object FarmKeys : Module(
    name = "Farm keys",
    description = "Temporarily changes your minecraft keybind configuration for farming in Skyblock.",
    category = Skit.CHEATS
) {
    private var prev: Int? = null
    private var prev0: Int? = null

    private val attackKey by KeybindSetting("Block breaking", GLFW.GLFW_KEY_UNKNOWN, "Changes the keybind for breaking blocks.")
    private val jumpKey by KeybindSetting("Jump", GLFW.GLFW_KEY_UNKNOWN, "Changes the keybind for jumping.")
    private val lockCamera by BooleanSetting("Lock camera", true, desc = "Locks your camera.")

    @JvmStatic
    val lock: Boolean
        get() = enabled && lockCamera

    override fun onEnable() {
        super.onEnable()

        prev = (mc.options?.keyAttack as? KeyMappingAccessor)?.boundKey?.value
        prev0 = (mc.options?.keyJump as? KeyMappingAccessor)?.boundKey?.value

        bind(attackKey.value, jumpKey.value)
    }

    override fun onDisable() {
        bind(prev ?: mc.options.keyAttack.defaultKey.value, prev0 ?: mc.options.keyJump.defaultKey.value)
        super.onDisable()
    }

    private fun bind(attackKeyCode: Int, jumpKeyCode: Int) {
        val options = mc.options ?: return

        if (attackKey.value != GLFW.GLFW_KEY_UNKNOWN) options.keyAttack.setKey(InputConstants.Type.KEYSYM.getOrCreate(attackKeyCode))
        if (jumpKey.value != GLFW.GLFW_KEY_UNKNOWN) options.keyJump.setKey(InputConstants.Type.KEYSYM.getOrCreate(jumpKeyCode))

        options.save()
        KeyMapping.resetMapping()
    }
}