package pt.graca.minebot.discord

import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import pt.graca.minebot.data.Data

class BotCommands(private val discord: Discord, private val data: Data) : ListenerAdapter() {

    private val commands = mutableListOf(
        Commands.slash("players", "Gets the online players")
    )

    override fun onGuildReady(event: GuildReadyEvent) {
        event.guild.updateCommands().addCommands(commands).queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "players" -> {
                discord.replyEmbedsToInteraction(
                    event,
                    "**${data.onlinePlayers}/${data.maxPlayers} Players Online**",
                    data.getPlayers().joinToString("\n") { it.name }
                )
            }
        }
    }
}