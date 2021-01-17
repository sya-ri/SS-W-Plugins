package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.core.code.StringEditor.toUncolor
import com.github.syari.ss.wplugins.core.message.JsonBuilder
import com.github.syari.ss.wplugins.core.message.JsonBuilder.Companion.buildJson
import com.github.syari.ss.wplugins.discord.DiscordMessageReceiveEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object Discord : Listener {
    var joinUrl: String? = null

    var listenChannels = mapOf<Long, ChatChannel>()

    @EventHandler
    fun on(e: DiscordMessageReceiveEvent) {
        if (e.member.isBot) return
        listenChannels[e.channel.id]?.let {
            val name = e.member.displayName
            val message = e.contentDisplay
            it.send(
                buildJson {
                    joinUrl?.let {
                        append("&d${name.toUncolor}", JsonBuilder.Hover.Text("&6Discord に参加する"), JsonBuilder.Click.OpenURL(it))
                    } ?: run {
                        append("&d${name.toUncolor}")
                    }
                    append("&d: ")
                    append(message.toUncolor)
                }
            )
        }
    }
}
