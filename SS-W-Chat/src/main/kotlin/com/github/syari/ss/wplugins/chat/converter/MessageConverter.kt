package com.github.syari.ss.wplugins.chat.converter

import com.github.syari.ss.wplugins.chat.converter.IMEConverter.toIME
import com.github.syari.ss.wplugins.chat.converter.KanaConverter.toKana

object MessageConverter {
    interface ConvertResult {
        val formatMessage: String

        class OnlyMessage(message: String): ConvertResult {
            override val formatMessage = "&f$message"
        }

        class WithConverted(message: String, converted: String): ConvertResult {
            override val formatMessage = "&f$converted &7($message)"
        }
    }

    fun convert(message: String): ConvertResult {
        return if (matchesHalfWidthChar(message)) {
            if (message.firstOrNull() == '.') {
                ConvertResult.OnlyMessage(message.substring(1))
            } else {
                val converted = message.toKana().toIME()
                if (message == converted) {
                    ConvertResult.OnlyMessage(message)
                } else {
                    ConvertResult.WithConverted(message, converted)
                }
            }
        } else {
            ConvertResult.OnlyMessage(message)
        }
    }

    private fun matchesHalfWidthChar(text: String): Boolean {
        return text.matches("^[a-zA-Z0-9!-/:-@\\[-`{-~\\s]*\$".toRegex())
    }
}