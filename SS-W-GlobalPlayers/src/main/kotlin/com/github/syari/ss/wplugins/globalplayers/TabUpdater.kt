package com.github.syari.ss.wplugins.globalplayers

import com.github.syari.ss.template.message.PluginMessageTemplateTabList
import com.github.syari.ss.wplugins.core.pluginMessage.PluginMessage
import com.github.syari.ss.wplugins.core.scheduler.CreateScheduler.runLater
import com.github.syari.ss.wplugins.globalplayers.Main.Companion.plugin
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerConnectedEvent
import net.md_5.bungee.api.event.ServerDisconnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object TabUpdater: Listener {
    private fun updateTabList() {
        plugin.runLater(5) {
            val playerList = plugin.proxy.players.map(ProxiedPlayer::getDisplayName)
            plugin.proxy.serversCopy.values.forEach {
                PluginMessage.send(it, PluginMessageTemplateTabList(playerList))
            }
        }
    }

    @EventHandler
    fun on(e: ServerConnectedEvent) {
        updateTabList()
    }

    @EventHandler
    fun on(e: ServerDisconnectEvent) {
        updateTabList()
    }
}