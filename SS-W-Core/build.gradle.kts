val project = Project.Core
group = project.group

dependencies {
    shadowApi(kotlin("stdlib-jdk8"))
}

bungee {
    name = project.name
    version = project.version
    main = project.main
    author = project.author
    depends = project.allDependPlugin
}
