package pt.graca.minebot.listeners

import org.bukkit.event.Listener
import pt.graca.minebot.data.Data
import pt.graca.minebot.discord.Discord

// add any listeners here
open class MinecraftListeners(private val discord: Discord, private val data: Data) {
    operator fun invoke() = mutableListOf<Listener>(
        PlayerListeners(discord, data)
    )
}