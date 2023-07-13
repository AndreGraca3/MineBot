package pt.graca.minebot.data

import org.bukkit.Bukkit

class Data {
    var visits = 0

    val onlinePlayers
        get() = Bukkit.getOnlinePlayers().size


    val maxPlayers
        get() = Bukkit.getMaxPlayers()

    fun getPlayers() = Bukkit.getOnlinePlayers().toList()
}