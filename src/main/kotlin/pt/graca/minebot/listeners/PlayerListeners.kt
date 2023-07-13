package pt.graca.minebot.listeners

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import pt.graca.minebot.data.Data
import pt.graca.minebot.discord.Discord


class PlayerListeners(private val discord: Discord, private val data: Data): Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        data.visits++
        event.joinMessage(Component.text("Welcome, ${event.player.name}"))

        val onlinePlayers = Bukkit.getOnlinePlayers().size
        discord.updatePresence(onlinePlayers)
        discord.sendPlayerJoinedMessage(event.player.name, onlinePlayers)
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        val onlinePlayers = Bukkit.getOnlinePlayers().size - 1  // for some reason, the player is still included
        discord.updatePresence(onlinePlayers)
    }
}