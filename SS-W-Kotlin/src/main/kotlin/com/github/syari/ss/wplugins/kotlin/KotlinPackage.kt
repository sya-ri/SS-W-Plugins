package com.github.syari.ss.wplugins.kotlin

object KotlinPackage {
    private val mutableList = mutableListOf<String>()

    val list
        get() = mutableList.toList()

    fun add(name: String) {
        mutableList.add(name)
    }
}
