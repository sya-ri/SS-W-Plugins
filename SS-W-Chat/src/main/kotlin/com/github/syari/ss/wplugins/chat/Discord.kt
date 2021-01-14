package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.chat.Main.Companion.plugin
import com.github.syari.ss.wplugins.core.code.StringEditor.toUncolor
import com.github.syari.ss.wplugins.core.message.JsonBuilder
import com.github.syari.ss.wplugins.core.message.JsonBuilder.Companion.buildJson
import com.github.syari.ss.wplugins.discord.DiscordMessageReceiveEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import com.github.syari.ss.wplugins.discord.Discord as DiscordAPI

object Discord : Listener {
    var joinUrl: String? = null
    var globalChannelId: Long? = null
    private val globalChannel by lazy { globalChannelId?.let { DiscordAPI.getTextChannel(it) } }

    @EventHandler
    fun on(e: DiscordMessageReceiveEvent) {
        if (e.member.isBot) return
        when (e.channel.id) {
            globalChannelId -> {
                val name = e.member.displayName
                val message = e.contentDisplay
                plugin.proxy.broadcast(
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

    fun sendToGlobal(message: String) {
        globalChannel?.send(message.toUncolor)
    }
}
