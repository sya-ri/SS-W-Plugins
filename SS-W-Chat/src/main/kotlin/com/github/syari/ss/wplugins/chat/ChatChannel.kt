package com.github.syari.ss.wplugins.chat

data class ChatChannel(val name: String) {
    companion object {
        private val list = mutableMapOf<String, ChatChannel>()

        fun get(name: String) = list.getOrPut(name) { ChatChannel(name) }
    }

    private val list = mutableSetOf<ChatSender>()

    fun addPlayers(players: Collection<ChatSender>) {
        list.addAll(players)
    }

    fun removePlayers(players: Collection<ChatSender>) {
        list.removeAll(players)
    }
}
