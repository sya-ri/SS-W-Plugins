package com.github.syari.ss.wplugins.core.scheduler

import com.github.syari.ss.wplugins.core.Main.Companion.plugin
import net.md_5.bungee.api.plugin.Plugin

object CreateScheduler {
    /**
     * @param plugin 実行するプラグイン
     * @param run 実行する処理
     * @return [CustomRunnable]
     */
    fun schedule(
        plugin: Plugin, run: CustomTask.() -> Unit
    ): CustomRunnable {
        return CustomRunnable(plugin, run)
    }

    /**
     * @param plugin 実行するプラグイン
     * @param run 実行する処理
     * @return [CustomTask]?
     */
    fun run(
        plugin: Plugin, run: CustomTask.() -> Unit
    ): CustomTask? {
        return schedule(plugin, run).run()
    }

    /**
     * @param plugin 実行するプラグイン
     * @param delay 遅らせる時間 tick
     * @param run 遅らせて実行する処理
     * @return [CustomTask]?
     */
    fun runLater(
        plugin: Plugin, delay: Long, run: CustomTask.() -> Unit
    ): CustomTask? {
        return schedule(plugin, run).runLater(delay)
    }

    /**
     * @param plugin 実行するプラグイン
     * @param period 繰り返し間隔 tick
     * @param delay 遅らせる時間 tick default: 0
     * @param run 繰り返し実行する処理
     * @return [CustomTask]?
     */
    fun runTimer(
        plugin: Plugin, period: Long, delay: Long = 0, run: CustomTask.() -> Unit
    ): CustomTask? {
        return schedule(plugin, run).runTimer(period, delay)
    }

    /**
     * @param plugin 実行するプラグイン
     * @param period 繰り返し間隔 tick
     * @param times 繰り返し回数
     * @param delay 遅らせる時間 tick default: 0
     * @param run 繰り返し実行する処理
     * @return [CustomTask]?
     */
    fun runRepeatTimes(
        plugin: Plugin, period: Long, times: Int, delay: Long = 0, run: CustomTask.() -> Unit
    ): CustomTask? {
        return schedule(plugin, run).runRepeatTimes(period, times, delay)
    }

    /**
     * @param listWithDelay キーを待機時間としたマップ
     * @param run 待機後に実行する処理
     * @return [Set]<[CustomTask]>
     */
    fun <T> runListWithDelay(
        listWithDelay: Map<Long, Set<T>>, run: (T) -> Unit
    ): Set<CustomTask> {
        return mutableSetOf<CustomTask>().also { taskList ->
            listWithDelay.forEach { (delay, value) ->
                runLater(plugin, delay) {
                    run(plugin) {
                        value.forEach {
                            run.invoke(it)
                        }
                    }
                    taskList.remove(this)
                }?.let { taskList.add(it) }
            }
        }
    }
}