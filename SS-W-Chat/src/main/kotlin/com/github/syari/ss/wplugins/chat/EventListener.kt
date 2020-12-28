package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.chat.Main.Companion.plugin
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object EventListener: Listener {
    @EventHandler
    fun on(e: ChatEvent) {
        val sender = e.sender
        if (sender !is ProxiedPlayer) return
        val chatSender = ChatSender.get(sender)
        if (chatSender.isGlobalChannel) {
            val message = chatSender.getGlobalTemplateMessage(sender, e.message)
            plugin.proxy.broadcast(message)
        }
    }
}