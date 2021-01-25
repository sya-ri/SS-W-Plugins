package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.discord.DiscordMessageReceiveEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object Discord : Listener {
    var joinUrl: String? = null

    var listenChannels = mapOf<Long, DiscordListenChannel>()

    @EventHandler
    fun on(e: DiscordMessageReceiveEvent) {
        if (e.member.isBot) return
        listenChannels[e.channel.id]?.let {
            val name = e.member.displayName
            val message = e.contentDisplay
            it.send(name, message)
        }
    }
}
