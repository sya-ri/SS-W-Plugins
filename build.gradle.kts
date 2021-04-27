import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bungee.BungeePluginDescription
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm") version "1.4.30"
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
    id("net.minecrell.plugin-yml.bungee") version "0.3.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    configure<KtlintExtension> {
        version.set("0.40.0")
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "net.minecrell.plugin-yml.bungee")

    val shadowImplementation by configurations.creating
    configurations["implementation"].extendsFrom(shadowImplementation)

    val project = Project.get(project.name) ?: error("Not Found Project ${project.name}")

    repositories {
        maven(url = "https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        when (project) {
            Project.Core -> {
                shadowImplementation(kotlin("stdlib-jdk8"))
            }
            Project.Discord -> {
                implementation("com.google.code.gson:gson:2.8.6")
                testImplementation("org.slf4j:slf4j-simple:1.7.30")
            }
            Project.Votifier -> {
                implementation("io.netty:netty-handler:4.1.53.Final")
                implementation("io.netty", "netty-transport-native-epoll", "4.1.53.Final", classifier = "linux-x86_64")
            }
        }
        implementation(kotlin("stdlib-jdk8"))
        implementation("io.github.waterfallmc:waterfall-api:1.16-R0.4-SNAPSHOT")
        project.dependProjectName.forEach { implementation(project(":$it")) }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }

    tasks.withType<ShadowJar> {
        configurations = listOf(shadowImplementation)
        classifier = null
        destinationDirectory.set(file("../jars"))
    }

    configure<BungeePluginDescription> {
        name = project.name
        version = project.version
        main = project.main
        author = project.author
        depends = project.allDependPlugin
    }
}

task("updateVersions") {
    doLast {
        val outputBlockComment = "<!-- Generate Versions -->"
        val escapedOutputBlockComment = Regex.escape(outputBlockComment)
        val mdFile = file("README.md")
        val fileContent = mdFile.bufferedReader().use { it.readText() }
        mdFile.writeText(
            fileContent.replace(
                "$escapedOutputBlockComment[\\s\\S]*$escapedOutputBlockComment".toRegex(),
                buildString {
                    appendln(outputBlockComment)
                    appendln(
                        """
                        | Name | Version |
                        |:-----|--------:|
                        """.trimIndent()
                    )
                    Project.list.sortedBy { it.name }.forEach {
                        appendln("| ${it.name} | ${it.version} |")
                    }
                    append(outputBlockComment)
                }
            )
        )
    }
}
