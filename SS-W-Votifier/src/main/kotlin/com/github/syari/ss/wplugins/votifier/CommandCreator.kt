package com.github.syari.ss.wplugins.votifier

import com.github.syari.ss.wplugins.core.code.OnEnable
import com.github.syari.ss.wplugins.core.command.CreateCommand.command
import com.github.syari.ss.wplugins.core.command.CreateCommand.element
import com.github.syari.ss.wplugins.core.command.CreateCommand.tab
import com.github.syari.ss.wplugins.votifier.BootstrapBuilder.reload
import com.github.syari.ss.wplugins.votifier.Main.Companion.plugin

object CommandCreator: OnEnable {
    override fun onEnable() {
        command(plugin, "votifier", "SS-Votifier", tab { (_, _) ->
            element("reload")
        }) { sender, args ->
            when (args.whenIndex(0)) {
                "reload" -> {
                    sendWithPrefix("コンフィグをリロードします")
                    reload(sender)
                }
                else -> {
                    sendHelp(
                        "votifier reload" to "コンフィグをリロードします"
                    )
                }
            }
        }
    }
}