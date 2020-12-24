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

    object Kotlin: Project {
        override val name = "SS-Kotlin"
        override val version = "1.4.21"
        override val group = subgroup("kotlin")
        override val dependProject = listOf<Project>()
    }
}