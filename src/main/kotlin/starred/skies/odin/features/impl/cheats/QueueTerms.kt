package starred.skies.odin.features.impl.cheats

import com.odtheking.odin.clickgui.settings.impl.NumberSetting
import com.odtheking.odin.events.GuiEvent
import com.odtheking.odin.events.TerminalEvent
import com.odtheking.odin.events.core.on
import com.odtheking.odin.features.Module
import com.odtheking.odin.features.impl.floor7.TerminalSolver
import com.odtheking.odin.features.impl.floor7.terminalhandler.TerminalTypes
import com.odtheking.odin.utils.devMessage
import starred.skies.odin.events.TerminalUpdateEvent
import starred.skies.odin.utils.Skit
import java.util.LinkedList
import java.util.Queue

object QueueTerms : Module(
    name = "Queue Terms",
    description = "Queues clicks in terminals to ensure every click is registered (only works in custom term gui).",
    category = Skit.CHEATS
) {
    private val dispatchDelay by NumberSetting("Dispatch Delay", 140L, 140L, 300L, unit = "ms", desc = "The delay between each click.")
    private data class Click(val slot: Int, val button: Int)
    private val queue: Queue<Click> = LinkedList()
    private var lastClickTime = 0L

    init {
        on<GuiEvent.CustomTermGuiClick> {
            with(TerminalSolver.currentTerm ?: return@on) {
                if (
                    type == TerminalTypes.MELODY ||
                    TerminalSolver.renderType != 1 ||
                    !isClicked ||
                    !canClick(slot, button)
                ) return@on

                queue.offer(Click(slot, button))
                simulateClick(slot, button)
                devMessage("§aQueued click on slot $slot")
                cancel()
            }
        }

        on<GuiEvent.DrawBackground> {
            with(TerminalSolver.currentTerm ?: return@on) {
                if (
                    type == TerminalTypes.MELODY ||
                    TerminalSolver.renderType != 1 ||
                    System.currentTimeMillis() - lastClickTime < dispatchDelay ||
                    queue.isEmpty() ||
                    isClicked
                ) return@on

                val click = queue.poll() ?: return@on

                click(click.slot, click.button, false)
                devMessage("§dDispatched click on slot ${click.slot}")
                lastClickTime = System.currentTimeMillis()
            }
        }

        on<TerminalUpdateEvent> {
            with (TerminalSolver.currentTerm ?: return@on) {
                if (TerminalSolver.renderType != 1 || queue.isEmpty()) return@on
                queue.forEach { simulateClick(it.slot, it.button) }
            }
        }

        on<TerminalEvent.Closed> {
            queue.clear()
        }
    }
}