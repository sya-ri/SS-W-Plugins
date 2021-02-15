open class Project(val version: String, groupName: String = "") {
    companion object {
        val list = mutableListOf<Project>()
    }

    constructor(buildVersion: Int) : this(buildVersion.toString())

    private val simpleName = javaClass.simpleName
    val name by lazy { "SS-W-${if (groupName.isEmpty()) "" else "$groupName-"}$simpleName" }
    val group by lazy { "com.github.syari.ss.wplugins.${if (groupName.isEmpty()) "" else "${groupName.toLowerCase()}."}${simpleName.toLowerCase()}" }
    val main = "$group.Main"
    val author = "sya_ri"
    open val dependProject = listOf<Project>()
    open val dependPlugin = listOf<String>()
    val dependProjectName by lazy { dependProject.map { it.name } }
    val allDependPlugin by lazy { (dependProjectName + dependPlugin).toSet() }

    init {
        list.add(this)
    }

    object AccessBlocker : Project(6) {
        override val dependProject = listOf(Core)
    }

    object Chat : Project(15) {
        override val dependProject = listOf(Core, Discord)
    }

    object Core : Project(13)

    object Discord : Project(5) {
        override val dependProject = listOf(Core)
    }

    object GlobalPlayers : Project(2) {
        override val dependProject = listOf(Core)
    }

    object PluginManager : Project(2) {
        override val dependProject = listOf(Core)
    }

    object Votifier : Project(3) {
        override val dependProject = listOf(Core)
    }
}
