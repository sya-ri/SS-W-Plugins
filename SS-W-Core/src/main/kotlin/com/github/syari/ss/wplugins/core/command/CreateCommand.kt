package com.github.syari.ss.wplugins.core.command

import com.github.syari.ss.wplugins.core.permission.Permission.isOp
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.TabExecutor

object CreateCommand {
    /**
     * 一般的なタブ補完
     * @param arg 引数が一致した場合にタブ補完されます
     * @param tab タブ補完の内容
     * @return [CommandTab.Base]
     */
    fun tab(
        vararg arg: String, tab: (Pair<CommandSender, CommandArgument>) -> CommandTabElement?
    ): CommandTab.Base {
        return CommandTab.Base(arg.toList(), tab)
    }

    /**
     * 設定のタブ補完
     * @param arg 引数が一致した場合にタブ補完されます
     * @param flag 設定に対応するタブ補完の内容
     * @return [CommandTab.Flag]
     */
    fun flag(
        arg: String, vararg flag: Pair<String, CommandTabElement>
    ): CommandTab.Flag {
        return CommandTab.Flag(arg, flag.toMap())
    }

    /**
     * タブ補完の要素
     * @param element 要素
     * @return [CommandTabElement]
     */
    fun element(element: Iterable<String>?): CommandTabElement {
        return CommandTabElement(element ?: listOf())
    }

    /**
     * タブ補完の要素
     * @param element 要素
     * @return [CommandTabElement]
     */
    fun element(vararg element: String): CommandTabElement {
        return element(element.toList())
    }

    /**
     * タブ補完の要素
     * @param condition 条件
     * @param element 条件に一致した場合の要素
     * @param unlessElement 条件に一致しなかった場合の要素
     * @return [CommandTabElement]
     */
    fun elementIf(
        condition: Boolean, element: Iterable<String>?, unlessElement: Iterable<String>? = listOf()
    ): CommandTabElement {
        return element(if (condition) element else unlessElement)
    }

    /**
     * タブ補完の要素
     * @param condition 条件
     * @param element 条件に一致した場合の要素
     * @param unlessElement 条件に一致しなかった場合の要素
     * @return [CommandTabElement]
     */
    fun elementIf(
        condition: Boolean, vararg element: String, unlessElement: Iterable<String>? = listOf()
    ): CommandTabElement {
        return elementIf(condition, element.toList(), unlessElement)
    }

    /**
     * タブ補完の要素
     * @param sender CommandSender
     * @param element sender.isOpが真であった場合の要素
     * @param unlessElement sender.isOpが偽であった場合の要素
     * @return [CommandTabElement]
     */
    fun elementIfOp(
        sender: CommandSender, element: Iterable<String>?, unlessElement: Iterable<String>? = listOf()
    ): CommandTabElement {
        return elementIf(sender.isOp, element, unlessElement)
    }

    /**
     * タブ補完の要素
     * @param sender CommandSender
     * @param element sender.isOpが真であった場合の要素
     * @param unlessElement sender.isOpが偽であった場合の要素
     * @return [CommandTabElement]
     */
    fun elementIfOp(
        sender: CommandSender, vararg element: String, unlessElement: Iterable<String>? = listOf()
    ): CommandTabElement {
        return elementIfOp(sender, element.toList(), unlessElement)
    }

    /**
     * コマンドを作成し、登録します
     * @param plugin 登録するプラグイン
     * @param label コマンド名 /label
     * @param messagePrefix メッセージの接頭
     * @param tabs タブ補完
     * @param execute コマンドの処理
     * @see CommandMessage
     * @see CommandArgument
     */
    fun command(
        plugin: Plugin, label: String, messagePrefix: String, vararg tabs: CommandTab, execute: CommandMessage.(CommandSender, CommandArgument) -> Unit
    ) {
        plugin.proxy.pluginManager.registerCommand(plugin, object: Command(label), TabExecutor {
            override fun execute(
                sender: CommandSender, args: Array<out String>
            ) {
                val message = CommandMessage(messagePrefix, sender)
                execute.invoke(
                    message, sender, CommandArgument(args, message)
                )
            }

            override fun onTabComplete(
                sender: CommandSender, args: Array<out String>
            ): MutableIterable<String> {
                return tabs.flatMap { tab ->
                    when (tab) {
                        is CommandTab.Base -> {
                            val element = tab.complete(
                                sender to CommandArgument(args, CommandMessage(messagePrefix, sender))
                            )?.element ?: return@flatMap listOf()
                            when {
                                tab.arg.isNotEmpty() -> tab.arg.flatMap { arg ->
                                    val splitArg = arg.split("\\s+".toRegex())
                                    if (splitArg.size <= args.size && splitArg.last() == "**") {
                                        element
                                    } else if (splitArg.size == args.lastIndex) {
                                        val completed = if (arg.contains('*')) {
                                            buildString {
                                                splitArg.forEachIndexed { index, word ->
                                                    append(if (word == "*") args[index] else word)
                                                }
                                            }.substringBeforeLast(" ")
                                        } else {
                                            arg
                                        }
                                        val joinArg = args.joinToString(" ").toLowerCase()
                                        element.filter {
                                            "$completed $it".toLowerCase().startsWith(joinArg)
                                        }
                                    } else {
                                        listOf()
                                    }
                                }
                                args.size == 1 -> element
                                else -> listOf()
                            }
                        }
                        is CommandTab.Flag -> {
                            val elementList = mutableSetOf<String>()
                            val splitArg = tab.arg.split("\\s+".toRegex())
                            splitArg.forEachIndexed { index, split ->
                                if (split != "*" && split.equals(args.getOrNull(index), true)) {
                                    return@flatMap listOf()
                                }
                            }
                            val enterText = args.getOrNull(args.size - 1) ?: return@flatMap listOf()
                            if ((args.lastIndex - splitArg.size) % 2 == 0) {
                                val element = tab.flag.keys.toMutableSet()
                                for (index in splitArg.size until args.lastIndex step 2) {
                                    element.remove(args[index].toLowerCase())
                                }
                                elementList.addAll(element)
                            } else {
                                tab.flag[args.getOrNull(args.size - 2)?.toLowerCase()]?.let {
                                    elementList.addAll(it)
                                }
                            }
                            elementList.filter {
                                it.toLowerCase().startsWith(enterText)
                            }
                        }
                    }
                }.sorted().toMutableSet()
            }
        })
    }
}