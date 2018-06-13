package com.baeldung.cdi.imagefileeditors;

import com.baeldung.cdi.qualifiers.JpgFileEditorQualifier;

@JpgFileEditorQualifier
public class JpgFileEditor implements ImageFileEditor {
   
    @Override
    public String openFile(String fileName) {
        return "Opening JPG file " + fileName;
    }

    @Override
    public String editFile(String fileName) {
        return "Editing JPG file " + fileName;
    }
    
    @Override
    public String writeFile(String fileName) {
        return "Writing JPG file " + fileName;
    }

    @Override
    public String saveFile(String fileName) {
        return "Saving JPG file " + fileName;
    }
}