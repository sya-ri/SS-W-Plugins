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
    val dependProjectName: List<String>
        get() = dependProject.map { it.name }
    val dependPlugin: List<String>
        get() = dependProjectName + extraDependPlugin
    val extraDependPlugin: List<String>
        get() = listOf()
}