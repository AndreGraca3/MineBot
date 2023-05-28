import mcs from 'node-mcstatus'
import data from './data.js'
import { findPlayerJoined } from './utils.js'
import * as discord from './discord.js'


export default function getServerStatus(host, port) {
    mcs.statusJava(host, port)
        .then(async (res) => {
            if(!res.online) return

            const mappedList = res.players?.list.map(p => p.name_clean)
            findPlayerJoined(data.players_list, mappedList)
            data.players_list = mappedList

            data.players_max = res.players.max
            data.icon = res.icon
            await discord.setActivity()

            // console.log('Refreshed data')
        })
        .catch((error) => {
            console.log(`Minecraft Bot error: ${error}`)
            // throw error
        })
        .finally(() => {
            setTimeout(() => getServerStatus(host, port), 60000)
        })
}
