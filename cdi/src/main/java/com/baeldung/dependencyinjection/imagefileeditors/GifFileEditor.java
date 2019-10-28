package com.baeldung.dependencyinjection.imagefileeditors;

import com.baeldung.dependencyinjection.qualifiers.GifFileEditorQualifier;

@GifFileEditorQualifier
public class GifFileEditor implements ImageFileEditor {
    
    @Override
    public String openFile(String fileName) {
        return "Opening GIF file " + fileName;
    }

    @Override
    public String editFile(String fileName) {
        return "Editing GIF file " + fileName;
    }
    
    @Override
    public String writeFile(String fileName) {
        return "Writing GIF file " + fileName;
    }

    @Override
    public String saveFile(String fileName) {
        return "Saving GIF file " + fileName;
    }
}