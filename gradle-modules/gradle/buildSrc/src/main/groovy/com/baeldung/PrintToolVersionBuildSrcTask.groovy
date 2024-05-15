package com.baeldung

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PrintToolVersionBuildSrcTask extends DefaultTask {
    String tool

    @TaskAction
    void printToolVersion() {
        switch (tool) {
            case 'java':
                println System.getProperty("java.version")
                break
            case 'groovy':
                println GroovySystem.version
                break
            default:
                throw new IllegalArgumentException("Unknown tool")
        }
    }
}