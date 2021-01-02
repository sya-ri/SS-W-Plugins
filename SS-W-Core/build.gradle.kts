val project = Project.Core
group = project.group

bungee {
    name = project.name
    version = project.version
    main = project.main
    author = project.author
    depends = project.allDependPlugin
}
