package com.github.syari.ss.wplugins.tablist

import com.github.syari.ss.wplugins.core.pluginMessage.PluginMessage
import com.github.syari.ss.wplugins.core.pluginMessage.template.PluginMessageTemplateTabList
import com.github.syari.ss.wplugins.tablist.Main.Companion.plugin
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.LoginEvent
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object EventListener: Listener {
    private fun updateTabList() {
        val playerList = plugin.proxy.players.map(ProxiedPlayer::getDisplayName)
        plugin.proxy.serversCopy.values.forEach {
            PluginMessage.send(it, PluginMessageTemplateTabList(playerList))
        }
    }

    @EventHandler
    fun on(e: LoginEvent) {
        updateTabList()
    }

    @EventHandler
    fun on(e: PlayerDisconnectEvent) {
        updateTabList()
    }
}