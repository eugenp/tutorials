package com.baeldung.kotlin

enum class UnixFileType {
    DIRECTORY, REGULAR_FILE, SYMBOLIC_LINK
}

sealed class UnixFile {

    abstract fun getObjectType(): String

    class RegularFile(val content: String) : UnixFile() {
        override fun getObjectType(): String {
            return "-"
        }
    }

    class Directory(val children: List<UnixFile>) : UnixFile() {
        override fun getObjectType(): String {
            return "d"
        }
    }

    class SymbolicLink(val originalFile: UnixFile) : UnixFile() {
        override fun getObjectType(): String {
            return "l"
        }
    }
}