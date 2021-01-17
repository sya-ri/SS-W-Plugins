package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.template.message.PluginMessageTemplateChatChannel
import com.github.syari.ss.wplugins.chat.Main.Companion.plugin
import com.github.syari.ss.wplugins.core.pluginMessage.SSPluginMessageEvent
import com.github.syari.ss.wplugins.core.scheduler.CreateScheduler.runSchedule
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object EventListener : Listener {
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

    @EventHandler
    fun on(e: SSPluginMessageEvent) {
        val template = e.template as? PluginMessageTemplateChatChannel ?: return
        val playerList = template.playerNameList.mapNotNull(plugin.proxy::getPlayer)
        val chatSenderList = playerList.map(ChatSender::get)
        val channel = ChatChannel.get(template.channelName)
        when (template.task) {
            PluginMessageTemplateChatChannel.UpdateTask.AddPlayer -> {
                channel.addPlayers(chatSenderList)
            }
            PluginMessageTemplateChatChannel.UpdateTask.RemovePlayer -> {
                channel.removePlayers(chatSenderList)
            }
            PluginMessageTemplateChatChannel.UpdateTask.SetSpeaker -> {
                chatSenderList.forEach { it.channel = channel }
            }
            PluginMessageTemplateChatChannel.UpdateTask.UnSetSpeaker -> {
                chatSenderList.forEach { it.channel = null }
            }
        }
    }
}
