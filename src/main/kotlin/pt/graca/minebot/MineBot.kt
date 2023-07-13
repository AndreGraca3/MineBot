package pt.graca.minebot

import org.bukkit.plugin.java.JavaPlugin
import pt.graca.minebot.data.Data
import pt.graca.minebot.discord.BotCommands
import pt.graca.minebot.discord.Discord
import pt.graca.minebot.listeners.MinecraftListeners


class MineBot : JavaPlugin() {

    private val data = Data()

    override fun onEnable() {
        val discord = Discord()
        val commands = BotCommands(discord, data)
        discord.registerEvents(commands)
        MinecraftListeners(discord, data)().forEach { server.pluginManager.registerEvents(it, this) }
        logger.info("Hello World!")
    }

    override fun onDisable() {
        logger.info("BYE!")
    }
}