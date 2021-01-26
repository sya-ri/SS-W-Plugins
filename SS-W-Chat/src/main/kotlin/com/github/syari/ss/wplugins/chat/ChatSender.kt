package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.chat.Main.Companion.plugin
import com.github.syari.ss.wplugins.chat.channel.ChatChannel
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.UUID

class ChatSender(private val uuid: UUID) {
    var channel: ChatChannel = ChatChannel.Global

    val player: ProxiedPlayer?
        get() = plugin.proxy.getPlayer(uuid)

    companion object {
        private val list = mutableMapOf<UUID, ChatSender>()

        fun get(player: ProxiedPlayer) = get(player.uniqueId)

        fun get(uuid: UUID) = list.getOrPut(uuid) { ChatSender(uuid) }
    }
}
