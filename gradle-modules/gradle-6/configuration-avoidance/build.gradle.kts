plugins {
    base
}

description = """
    Demonstrates Gradle Configuraiton Avoidance API. Creates a new configuration "extralibs" to 
    which we add dependencies. The custom task "copyExtraLibs" copies those dependencies to a new 
    build directory "extra-libs". This build uses the Task Configuraion Avoidance APIs which have 
    been marked stable in Gradle 6.0
""".trimIndent()

// extraLibs is a NamedDomainObjectProvider<Configuration!> - the Configuration object will not be
// realized until it is needed. In the meantime, the build may reference it by name
val extralibs by configurations.registering

dependencies {
    // we can call extralibs.name without causing the extralibs to be realized
    add(extralibs.name, "junit:junit:4.12")
}

// extraLibsDir is a Provider<Directory!> - the Directory object will not be realized until it is
// needed
val extraLibsDir = project.layout.buildDirectory.dir("extra-libs")

// copyExtraLibs is a TaskProvider<Copy!> - the task will not be realized until it is needed
val copyExtraLibs by tasks.registering(Copy::class) {
    // the copy task's "from" and "into" APIs accept Provider types to support configuration
    // avoidance
    from(extralibs)
    into(extraLibsDir)
}

// configures the "build" task only if it needs to be
tasks.build {
    // dependsOn accepts a TaskProvider to avoid realizing the copyExtraLibs needlessly
    dependsOn(copyExtraLibs)
}
