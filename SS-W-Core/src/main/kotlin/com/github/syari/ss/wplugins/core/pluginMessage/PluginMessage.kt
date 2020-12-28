package com.github.syari.ss.wplugins.core.pluginMessage

import com.github.syari.ss.wplugins.core.Main.Companion.plugin
import com.github.syari.ss.wplugins.core.code.OnEnable
import com.github.syari.ss.wplugins.core.pluginMessage.template.PluginMessageTemplate
import com.google.common.io.ByteStreams
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object PluginMessage: OnEnable, Listener {
    override fun onEnable() {
        plugin.proxy.registerChannel(PluginMessageTemplate.ChannelName)
    }

    @EventHandler
    fun on(e: PluginMessageEvent) {
        if (e.tag.equals(PluginMessageTemplate.ChannelName, true)) {
            PluginMessageTemplate.readFrom(ByteStreams.newDataInput(e.data))?.let {
                SSPluginMessageEvent(it).callEvent()
            }
        }
    }

    fun send(server: ServerInfo, template: PluginMessageTemplate) {
        val dataOutput = ByteStreams.newDataOutput()
        dataOutput.writeUTF(template.subChannel)
        template.writeTo(dataOutput)
        server.sendData(PluginMessageTemplate.ChannelName, dataOutput.toByteArray())
    }
}