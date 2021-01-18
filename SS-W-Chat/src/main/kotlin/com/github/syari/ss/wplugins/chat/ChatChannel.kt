package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.chat.Main.Companion.plugin
import com.github.syari.ss.wplugins.chat.converter.MessageConverter
import com.github.syari.ss.wplugins.core.code.StringEditor.toUncolor
import com.github.syari.ss.wplugins.core.message.JsonBuilder
import com.github.syari.ss.wplugins.core.message.JsonBuilder.Companion.buildJson
import com.github.syari.ss.wplugins.core.message.Message.send
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer

sealed class ChatChannel(val name: String) {
    companion object {
        fun get(name: String) = if (name == Global.name) Global else Private.get(name)

        fun getOrNull(name: String) = if (name == Global.name) Global else Private.getOrNull(name)

        val nameList
            get() = listOf(Global.name) + Private.nameList
    }

    protected val options = ChatChannelOption.get(name)

    abstract fun send(message: TextComponent)

    fun send(player: ProxiedPlayer, message: String) {
        send(
            buildJson {
                val prefix = options.firstOrNull { it.prefix != null }?.prefix
                val name = player.displayName
                val serverName = player.server.info.name
                if (prefix != null) {
                    append("$prefix&r ")
                }
                append("&b$name", JsonBuilder.Hover.Text("&bServer: &f$serverName"))
                append("&b: ")
                append(MessageConverter.convert(message.toUncolor).formatMessage)
            }
        )
        options.forEach { it.discordChannel?.send(it.templateDiscord.get(name, player.displayName, message)) }
    }

    object Global : ChatChannel("global") {
        override fun send(message: TextComponent) {
            plugin.proxy.broadcast(message)
        }
    }

    class Private(name: String) : ChatChannel(name) {
        companion object {
            private val list = mutableMapOf<String, Private>()

            fun get(name: String) = list.getOrPut(name) { Private(name) }

            fun getOrNull(name: String) = list[name]

            val nameList
                get() = list.keys
        }

        private val list = mutableSetOf<ChatSender>()

        init {
            addPlayers(options.flatMap(ChatChannelOption::players).map(ChatSender::get))
        }

        fun addPlayers(players: Collection<ChatSender>) {
            list.addAll(players)
        }

        fun removePlayers(players: Collection<ChatSender>) {
            list.removeAll(players)
        }

        override fun send(message: TextComponent) {
            list.forEach { it.player?.send(message) }
        }
    }
}
