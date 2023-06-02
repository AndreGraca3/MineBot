import serverStatus from './serverStatus.js'
import { client } from './discord.js'
import { TOKEN } from './secrets.js'


const host = 'gracabot.duckdns.org'
const port = 25565

client.login(TOKEN)


client.on("ready", async () => {
    console.log("Bot is Online")
    serverStatus(host, port)
})