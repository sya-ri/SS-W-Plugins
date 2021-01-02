package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.chat.Main.Companion.plugin
import com.github.syari.ss.wplugins.core.scheduler.CreateScheduler.runSchedule
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object ChatListener: Listener {
    @EventHandler
    fun on(e: ChatEvent) {
        if (e.message.firstOrNull() == '/') return
        e.isCancelled = true
        val sender = e.sender
        if (sender !is ProxiedPlayer) return
        val chatSender = ChatSender.get(sender)
        if (chatSender.isGlobalChannel) {
            plugin.runSchedule {
                val message = chatSender.getGlobalTemplateMessage(sender, e.message)
                plugin.proxy.broadcast(message)
                Discord.sendToGlobal(message.toPlainText())
            }
        }
    }
}