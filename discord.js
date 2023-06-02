import discord, { Colors } from 'discord.js'
import { CLIENT_ID, GUILD_ID, CHANNEL_ID, TOKEN } from './secrets.js'
import commands from './commands.js'
import data from './data.js'
import * as utils from './utils.js'


export const client = new discord.Client(
    {
        intents: [
            discord.GatewayIntentBits.Guilds,
            discord.GatewayIntentBits.GuildMessages,
            discord.GatewayIntentBits.MessageContent,
            discord.GatewayIntentBits.GuildEmojisAndStickers
        ],
    })

client.on("interactionCreate", async (interaction) => {
    try {
        if (interaction.user.bot) return
        if (interaction.channel.isDMBased()) return

        if (interaction.commandName == "players") {
            if (data.players_list == 0) throw new utils.EmptyListError()
            const embed = new discord.EmbedBuilder()
                .setColor(Colors.Orange)
                .setDescription(`${utils.createVerticalList(data.players_list)}`)
                .setTitle(`:bust_in_silhouette: ${data.players_list.length}/${data.players_max} Players Online`)
                .setTimestamp()
            interaction.reply({ embeds: [embed] });
        }

        if (interaction.commandName == "uptime") {
            const embed = new discord.EmbedBuilder()
                .setColor(Colors.Purple)
                .setDescription(`${utils.format(process.uptime())}`)
                .setTitle(":clock1: Uptime")
                .setTimestamp()
            interaction.reply({ embeds: [embed] });
        }

    } catch (e) {
        const embed = new discord.EmbedBuilder()
            .setColor(Colors.Red)
            .setTitle(`:red_circle: ${e.message}`)
            .setThumbnail('https://i.imgur.com/3jZlE66.png')
            .setTimestamp()
        interaction.reply({ embeds: [embed] });
    }
})

const rest = new discord.REST({ version: '10' }).setToken(TOKEN);

(async () => {
    try {
        console.log(`Started refreshing ${commands.length} Discord (/) commands.`)

        const data = await rest.put(
            discord.Routes.applicationGuildCommands(CLIENT_ID, GUILD_ID),
            { body: commands }
        )

    } catch (error) {
        console.error(error)
    }
})()

export function setActivity(offline) {
    const msg = offline ? "🔴 Server Offline" : `${data.players_list.length} Players`
    client.user.setActivity(`${msg}`, { type: discord.ActivityType.Watching })
}

export async function notifyJoin(player) {
    const embed = new discord.EmbedBuilder()
        .setColor(Colors.Green)
        .addFields(
            { name: 'Players Online', value: `${data.players_list.length + 1}`, inline: true }
        )
        .setTitle(`:warning: ${player} entered`)
        .setTimestamp();
    (await client.channels.fetch(CHANNEL_ID)).send({ content: "<@&1081609569047941232>", embeds: [embed] })
}