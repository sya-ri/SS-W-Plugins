package com.github.syari.ss.wplugins.chat.converter

import java.io.FileNotFoundException
import java.net.URL
import java.net.URLEncoder

object IMEConverter {
    private const val GOOGLE_IME_URL = "https://www.google.com/transliterate?langpair=ja-Hira|ja&text="

    fun String.toIME(): String {
        val readFile = try {
            val url = URL(GOOGLE_IME_URL + URLEncoder.encode(this, "utf-8"))
            url.readText()
        } catch (ex: FileNotFoundException) {
            return this
        }
        var level = 0
        return buildString {
            var index = 0
            while (index < readFile.length) {
                if (level < 3) {
                    val begin = readFile.indexOf("[", index)
                    val end = readFile.indexOf("]", index)
                    index = when {
                        begin == -1 -> {
                            return toString()
                        }
                        begin < end -> {
                            level++
                            begin + 1
                        }
                        else -> {
                            level--
                            end + 1
                        }
                    }
                } else {
                    val begin = readFile.indexOf("\"", index)
                    val end = readFile.indexOf("\"", begin + 1)
                    if (begin == -1 || end == -1) return toString()
                    append(readFile.substring(begin + 1, end))
                    val next = readFile.indexOf("]", end)
                    if (next == -1) {
                        return toString()
                    } else {
                        level--
                        index = next + 1
                    }
                }
            }
        }
    }
}
