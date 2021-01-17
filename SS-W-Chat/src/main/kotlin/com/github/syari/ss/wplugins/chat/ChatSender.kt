package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.chat.converter.MessageConverter
import com.github.syari.ss.wplugins.core.code.StringEditor.toColor
import com.github.syari.ss.wplugins.core.code.StringEditor.toUncolor
import com.github.syari.ss.wplugins.core.message.JsonBuilder
import com.github.syari.ss.wplugins.core.message.JsonBuilder.Companion.buildJson
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.UUID

class ChatSender {
    var isGlobalChannel = true

    fun getGlobalTemplateMessage(player: ProxiedPlayer, message: String): TextComponent {
        val name = player.displayName
        val serverName = player.server.info.name
        return buildJson {
            append(
                "&b$name".toColor, JsonBuilder.Hover.Text("&bServer: &f$serverName")
            )
            append("&b: ")
            append(MessageConverter.convert(message.toUncolor).formatMessage)
        }
    }

    var channel: ChatChannel? = null

    companion object {
        private val list = mutableMapOf<UUID, ChatSender>()

        fun get(player: ProxiedPlayer) = list.getOrPut(player.uniqueId, ::ChatSender)
    }
}
