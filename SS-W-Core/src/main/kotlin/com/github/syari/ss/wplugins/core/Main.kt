package com.github.syari.ss.wplugins.core

import com.github.syari.ss.wplugins.core.code.SSPlugin
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Plugin

class Main: SSPlugin() {
    companion object {
        internal lateinit var plugin: Plugin

        /**
         * コンソール
         */
        lateinit var console: CommandSender
    }

    override fun onEnable() {
        plugin = this
        console = proxy.console
    }
}