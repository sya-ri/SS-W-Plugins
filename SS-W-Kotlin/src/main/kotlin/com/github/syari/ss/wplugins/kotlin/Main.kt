package com.github.syari.ss.wplugins.kotlin

import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Plugin

class Main : Plugin() {
    private val kotlinDescription
        get() = """
                §6--------[ §a§lSS-Kotlin §6]--------
                §6 * §dkotlin-jvm §7- §dversion ${KotlinVersion.CURRENT}
                ${KotlinPackage.list.joinToString(separator = "\n") { "§6 * §d$it" }}
                §6-----------------------------
        """.trimIndent()

    override fun onLoad() {
        KotlinPackage.add("stdlib-jdk8")
    }

    override fun onEnable() {
        proxy.console.sendMessage(*TextComponent.fromLegacyText(kotlinDescription))
    }
}
