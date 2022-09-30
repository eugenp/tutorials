package com.baeldung.io

import org.junit.Test

import groovy.io.FileType
import groovy.io.FileVisitResult

class TraverseFileTreeUnitTest {
    @Test
    void whenUsingEachFile_filesAreListed() {
        new File('src/main/resources').eachFile { file ->
            println file.name
        }
    }
    
    @Test(expected = IllegalArgumentException)
    void whenUsingEachFileOnAFile_anErrorOccurs() {
        new File('src/main/resources/ioInput.txt').eachFile { file ->
            println file.name
        }
    }
    
    @Test
    void whenUsingEachFileMatch_filesAreListed() {
        new File('src/main/resources').eachFileMatch(~/io.*\.txt/) { file ->
            println file.name
        }
    }
    
    @Test
    void whenUsingEachFileRecurse_thenFilesInSubfoldersAreListed() {
        new File('src/main').eachFileRecurse(FileType.FILES) { file ->
            println "$file.parent $file.name"
        }
    }
    
    @Test
    void whenUsingEachFileRecurse_thenDirsInSubfoldersAreListed() {
        new File('src/main').eachFileRecurse(FileType.DIRECTORIES) { file ->
            println "$file.parent $file.name"
        }
    }
    
    @Test
    void whenUsingEachDirRecurse_thenDirsAndSubDirsAreListed() {
        new File('src/main').eachDirRecurse { dir ->
            println "$dir.parent $dir.name"
        }
    }
    
    @Test
    void whenUsingTraverse_thenDirectoryIsTraversed() {
        new File('src/main').traverse { file ->
            if (file.directory && file.name == 'groovy') {
                FileVisitResult.SKIP_SUBTREE
            } else {
                println "$file.parent - $file.name"
            }
        }
    }
}
