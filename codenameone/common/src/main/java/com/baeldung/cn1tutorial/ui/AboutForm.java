package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.service.AppContext;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 * Simple informational screen with a short description and an external link.
 */
public class AboutForm extends BaseForm {
    /**
     * @param context shared runtime context
     * @param previousForm previous form for back navigation
     */
    public AboutForm(AppContext context, Form previousForm) {
        super(context, "about.title", previousForm);
        com.codename1.ui.Container content = new com.codename1.ui.Container(BoxLayout.y());
        content.setScrollableY(true);
        SpanLabel intro = new SpanLabel(context.text("about.body"));
        intro.setName("aboutBodyLabel");
        intro.setUIID("SectionText");
        Button baeldungLink = new Button(context.text("about.baeldung.link"));
        baeldungLink.setName("aboutBaeldungLink");
        baeldungLink.setUIID("InlineLink");
        FontImage.setMaterialIcon(baeldungLink, FontImage.MATERIAL_OPEN_IN_NEW, 3.5f);
        baeldungLink.addActionListener(evt -> com.codename1.ui.CN.execute("https://www.baeldung.com/"));
        content.add(intro);
        content.add(baeldungLink);
        add(BorderLayout.CENTER, content);
    }
}
