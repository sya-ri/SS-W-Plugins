import com.github.syari.ss.wplugins.discord.api.DiscordAPI
import com.github.syari.ss.wplugins.discord.api.DiscordAPI.LOGGER
import com.github.syari.ss.wplugins.discord.api.entity.TextChannel

fun main() {
    DiscordAPI.login(BOT_TOKEN) {}
    Thread.sleep(1000)
    TextChannel.get(TEST_TEXT_CHANNEL)?.send("Hello!!")
    LOGGER.debug("return")
    while (true) {
    }
}