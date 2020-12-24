val project = Project.Kotlin
group = project.group

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
}

bungee {
    name = project.name
    version = project.version
    main = project.main
    author = project.author
    depends = project.dependPlugin
}
