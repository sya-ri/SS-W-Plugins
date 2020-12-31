package com.github.syari.ss.wplugins.accessblocker

object ModList {
    var availableList = listOf<String>()

    fun isAvailable(name: String) = availableList.contains(name)
}