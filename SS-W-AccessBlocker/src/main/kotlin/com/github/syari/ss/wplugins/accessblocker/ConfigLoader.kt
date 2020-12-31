package com.github.syari.ss.wplugins.accessblocker

import com.github.syari.ss.wplugins.accessblocker.Main.Companion.plugin
import com.github.syari.ss.wplugins.core.Main.Companion.console
import com.github.syari.ss.wplugins.core.code.OnEnable
import com.github.syari.ss.wplugins.core.config.CreateConfig.config
import com.github.syari.ss.wplugins.core.config.dataType.ConfigDataType
import net.md_5.bungee.api.CommandSender
import java.util.UUID

object ConfigLoader: OnEnable {
    override fun onEnable() {
        load(console)
    }

    fun load(sender: CommandSender) {
        config(plugin, sender, "config.yml") {
            ModList.availableList = get("mod", ConfigDataType.STRINGLIST, listOf(), false)
            WhiteList.isEnable = get("whitelist.enable", ConfigDataType.BOOLEAN, default = true, false)
            val uuidList = get("whitelist.uuid", ConfigDataType.STRINGLIST, listOf(), false)
            WhiteList.list = uuidList.map { UUID.fromString(it) }.toMutableList()
        }
    }
}