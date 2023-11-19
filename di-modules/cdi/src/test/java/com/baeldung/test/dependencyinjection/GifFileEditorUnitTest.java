package com.baeldung.test.dependencyinjection;

import com.baeldung.dependencyinjection.imagefileeditors.GifFileEditor;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class GifFileEditorUnitTest {
    
    private static GifFileEditor gifFileEditor;
    
    
    @BeforeClass
    public static void setGifFileEditorInstance() {
        gifFileEditor = new GifFileEditor();
    }
    
    @Test
    public void givenGifFileEditorlInstance_whenCalledopenFile_thenOneAssertion() {
        assertThat(gifFileEditor.openFile("file1.gif")).isEqualTo("Opening GIF file file1.gif");
    }
    
    @Test
    public void givenGifFileEditorlInstance_whenCallededitFile_thenOneAssertion() {
        assertThat(gifFileEditor.editFile("file1.gif")).isEqualTo("Editing GIF file file1.gif");
    }
    
    @Test
    public void givenGifFileEditorInstance_whenCalledwriteFile_thenOneAssertion() {
        assertThat(gifFileEditor.writeFile("file1.gif")).isEqualTo("Writing GIF file file1.gif");
    }
    
    @Test
    public void givenGifFileEditorInstance_whenCalledsaveFile_thenOneAssertion() {
        assertThat(gifFileEditor.saveFile("file1.gif")).isEqualTo("Saving GIF file file1.gif");
    }
}