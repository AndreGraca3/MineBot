import * as discord from './discord.js'


export function createVerticalList(items) {
    let res = "\u200B"
    items.forEach(i => res += `${i}\n`)
    return res
}

export function format(seconds) {
    function pad(s) {
        return (s < 10 ? '0' : '') + s;
    }
    var hours = Math.floor(seconds / (60 * 60));
    var minutes = Math.floor(seconds % (60 * 60) / 60);
    var seconds = Math.floor(seconds % 60);

    return pad(hours) + ':' + pad(minutes) + ':' + pad(seconds);
}

export class EmptyListError extends Error {
    constructor() {
      super("List is empty.")
      this.name = 'EmptyListError'
    }
}

export function findPlayerJoined(oldList, list) {
    for (let i = 0; i < list.length; i++) {
      const player = list[i]
      if (!oldList.includes(player)) {
        discord.notifyJoin(player)
      }
    }
  
    return null;
  }