package com.baeldung.io

import groovy.io.FileType
import groovy.io.FileVisitResult
import org.junit.Test

import static org.junit.Assert.assertTrue

class TraverseFileTreeUnitTest {
    @Test
    void whenUsingEachFile_filesAreListed() {
        var files = []
        new File('src/main/resources').eachFile { file ->
            files.add(file.name)
        }
        assertTrue(files.size() > 1)
    }
    
    @Test(expected = IllegalArgumentException)
    void whenUsingEachFileOnAFile_anErrorOccurs() {
        var files = []
        new File('src/main/resources/ioInput.txt').eachFile { file ->
            files.add(file.name)
        }
    }
    
    @Test
    void whenUsingEachFileMatch_filesAreListed() {
        var files = []
        new File('src/main/resources').eachFileMatch(~/io.*\.txt/) { file ->
            files.add(file.name)
        }
    }

    @Test
    void whenUsingEachFileRecurse_thenFilesInSubfoldersAreListed() {
        var files = []
        new File('src/main').eachFileRecurse(FileType.FILES) { file ->
            files.add("$file.parent $file.name")
        }
    }

    @Test
    void whenUsingEachFileRecurse_thenDirsInSubfoldersAreListed() {
        var files = []
        new File('src/main').eachFileRecurse(FileType.DIRECTORIES) { file ->
            files.add("$file.parent $file.name")
        }
    }

    @Test
    void whenUsingEachDirRecurse_thenDirsAndSubDirsAreListed() {
        var files = []
        new File('src/main').eachDirRecurse { dir ->
            files.add("$dir.parent $dir.name")
        }
    }

    @Test
    void whenUsingTraverse_thenDirectoryIsTraversed() {
        var files = []
        new File('src/main').traverse { file ->
            if (file.directory && file.name == 'groovy') {
                FileVisitResult.SKIP_SUBTREE
            } else {
                files.add("$file.parent - $file.name")
            }
        }
        assertTrue(files.size() > 1)
    }
}
