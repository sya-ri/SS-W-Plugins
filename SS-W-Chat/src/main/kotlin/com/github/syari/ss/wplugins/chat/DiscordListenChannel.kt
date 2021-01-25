package com.github.syari.ss.wplugins.chat

import com.github.syari.ss.wplugins.core.code.StringEditor.toUncolor
import com.github.syari.ss.wplugins.core.message.JsonBuilder
import com.github.syari.ss.wplugins.core.message.JsonBuilder.Companion.buildJson

class DiscordListenChannel(
    private val prefix: String,
    private val chatChannel: ChatChannel
) {
    fun send(name: String, message: String) {
        chatChannel.send(
            buildJson {
                append(
                    prefix + name.toUncolor,
                    JsonBuilder.Hover.Text("&6Discord に参加する"),
                    JsonBuilder.Click.OpenURL(Discord.joinUrl.toString())
                )
                append("&d: ")
                append(message.toUncolor)
            }
        )
        chatChannel.sendConsoleLog(name, message, true)
    }
}
