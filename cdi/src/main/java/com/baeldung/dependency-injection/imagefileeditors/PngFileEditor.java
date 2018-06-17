package com.baeldung.dependency-injection.imagefileeditors;

import com.baeldung.dependency-injection.qualifiers.PngFileEditorQualifier;

@PngFileEditorQualifier
public class PngFileEditor implements ImageFileEditor {
   
    @Override
    public String openFile(String fileName) {
        return "Opening PNG file " + fileName;
    }

    @Override
    public String editFile(String fileName) {
        return "Editing PNG file " + fileName;
    }
    
    @Override
    public String writeFile(String fileName) {
        return "Writing PNG file " + fileName;
    }

    @Override
    public String saveFile(String fileName) {
        return "Saving PNG file " + fileName;
    }
}