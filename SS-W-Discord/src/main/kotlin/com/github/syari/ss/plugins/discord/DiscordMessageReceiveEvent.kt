package com.github.syari.ss.plugins.discord

import com.github.syari.ss.plugins.discord.api.entity.Message
import com.github.syari.ss.wplugins.core.event.CustomEvent

class DiscordMessageReceiveEvent(val message: Message): CustomEvent() {
    inline val channel
        get() = message.channel
    inline val member
        get() = message.member
    inline val content
        get() = message.content
    inline val contentDisplay
        get() = message.contentDisplay
}