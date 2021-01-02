package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.chat.Main.Companion.plugin
import com.github.syari.ss.wplugins.core.Main.Companion.console
import com.github.syari.ss.wplugins.core.code.OnEnable
import com.github.syari.ss.wplugins.core.config.CreateConfig.config
import com.github.syari.ss.wplugins.core.config.dataType.ConfigDataType

object ConfigLoader: OnEnable {
    override fun onEnable() {
        plugin.config(console, "config.yml") {
            Discord.joinUrl = get("discord.url", ConfigDataType.STRING, false)
            Discord.globalChannelId = get("discord.global", ConfigDataType.LONG, false)
        }
    }
}