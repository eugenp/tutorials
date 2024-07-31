package com.baeldung.enummapping.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import com.baeldung.enummapping.enums.Level;

public class LevelEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if (StringUtils.isBlank(text)) {
            setValue(null);
        } else {
            setValue(EnumUtils.getEnum(Level.class, text.toUpperCase()));
        }

    }

}
