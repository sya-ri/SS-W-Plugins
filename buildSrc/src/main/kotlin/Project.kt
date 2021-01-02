open class Project(val version: String, groupName: String = "") {
    constructor(buildVersion: Int): this(buildVersion.toString())

    private val simpleName = javaClass.simpleName
    val name by lazy { "SS-W-${if (groupName.isEmpty()) "" else "$groupName-"}$simpleName" }
    val group by lazy { "com.github.syari.ss.wplugins.${if (groupName.isEmpty()) "" else "${groupName.toLowerCase()}."}${simpleName.toLowerCase()}" }
    val main = "$group.Main"
    val author = "sya_ri"
    open val dependProject = listOf<Project>()
    open val dependPlugin = listOf<String>()
    val dependProjectName by lazy { dependProject.map { it.name } }
    val allDependPlugin by lazy { (dependProjectName + dependPlugin).toSet() }

    object AccessBlocker: Project(3) {
        override val dependProject = listOf(Core)
    }

    object Chat: Project(4) {
        override val dependProject = listOf(Core, Discord)
    }

    object Core: Project(7) {
        override val dependProject = listOf(Kotlin)
    }

    object Discord: Project(2) {
        override val dependProject = listOf(Core)
    }

    object GlobalPlayers: Project(1) {
        override val dependProject = listOf(Core)
    }

    object Kotlin: Project("1.4.21")

    object PluginManager: Project(2) {
        override val dependProject = listOf(Core)
    }

    object Votifier: Project(2) {
        override val dependProject = listOf(Core)
    }
}