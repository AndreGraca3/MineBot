package pt.graca.minebot.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import pt.graca.minebot.config.CHANNEL_ID
import pt.graca.minebot.config.TOKEN
import java.awt.Color
import java.lang.IllegalArgumentException
import java.time.OffsetDateTime

class Discord {

    private val jda = JDABuilder.createDefault(TOKEN)
        .setActivity(Activity.watching("0 Players"))
        .build()

    inner class EmbeddedField(val name: String, val value: String, val inline: Boolean)

    private fun createEmbedded(
        title: String,
        description: String? = null,
        color: Color = Color.ORANGE,
        vararg fields: EmbeddedField
    ): MessageEmbed {
        val embedded = EmbedBuilder()
            .setTitle(title)
            .setColor(color)
            .setTimestamp(OffsetDateTime.now())
        if (description != null) embedded.setDescription(description)
        fields.forEach { embedded.addField(it.name, it.value, it.inline) }
        return embedded.build()
    }

    fun sendEmbedsToChannel(
        channel: TextChannel,
        title: String,
        description: String? = null,
        color: Color = Color.ORANGE,
        vararg fields: EmbeddedField
    ) {
        channel.sendMessageEmbeds(createEmbedded(title, description, color, *fields)).queue()
    }

    fun replyEmbedsToInteraction(
        event: SlashCommandInteractionEvent,
        title: String,
        description: String?,
        color: Color = Color.ORANGE,
        vararg fields: EmbeddedField
    ) {
        event.replyEmbeds(createEmbedded(title, description, color, *fields)).queue()
    }

    fun sendPlayerJoinedMessage(playerName: String, onlinePlayers: Int) {
        val channel = jda.getTextChannelById(CHANNEL_ID)
            ?: throw IllegalArgumentException("The channel id $CHANNEL_ID does not correspond to an existing channel")

        sendEmbedsToChannel(channel,
            "⚠️ $playerName entered", "<@&1081609569047941232>", Color.GREEN, EmbeddedField(
                "**Players Online**",
                onlinePlayers.toString(), false
            )
        )
    }

    fun updatePresence(onlinePlayers: Int) {
        jda.presence.setPresence(Activity.watching("$onlinePlayers Players"), false)
    }

    fun registerEvents(commands: BotCommands) {
        jda.addEventListener(commands)
    }
}