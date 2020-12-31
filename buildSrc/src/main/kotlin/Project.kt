interface Project {
    companion object {
        private const val group = "com.github.syari.ss.wplugins"

        fun subgroup(path: String) = "$group.$path"

        fun build(number: Int) = number.toString()
    }

    val name: String
    val version: String
    val group: String
    val main: String
        get() = "$group.Main"
    val author: String
        get() = "sya_ri"
    val dependProject: List<Project>
    val dependProjectName: Set<String>
        get() = dependProject.map { it.name }.toSet()
    val dependPlugin: Set<String>
        get() = dependProjectName + extraDependPlugin
    val extraDependPlugin: Set<String>
        get() = setOf()

    object AccessBlocker: Project {
        override val name = "SS-W-AccessBlocker"
        override val version = build(1)
        override val group = subgroup("accessblocker")
        override val dependProject = listOf(Core)
    }

    object Chat: Project {
        override val name = "SS-W-Chat"
        override val version = build(3)
        override val group = subgroup("chat")
        override val dependProject = listOf(Core)
    }

    object Core: Project {
        override val name = "SS-W-Core"
        override val version = build(7)
        override val group = subgroup("core")
        override val dependProject = listOf(Kotlin)
    }

    object Discord: Project {
        override val name = "SS-W-Discord"
        override val version = build(1)
        override val group = subgroup("discord")
        override val dependProject = listOf(Core)
    }

    object GlobalPlayers: Project {
        override val name = "SS-W-GlobalPlayers"
        override val version = build(1)
        override val group = subgroup("globalplayers")
        override val dependProject = listOf(Core)
    }

    object Kotlin: Project {
        override val name = "SS-W-Kotlin"
        override val version = "1.4.21"
        override val group = subgroup("kotlin")
        override val dependProject = listOf<Project>()
    }

    object Votifier: Project {
        override val name = "SS-W-Votifier"
        override val version = build(2)
        override val group = subgroup("votifier")
        override val dependProject = listOf(Core)
    }
}