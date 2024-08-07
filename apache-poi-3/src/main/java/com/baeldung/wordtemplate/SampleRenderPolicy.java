package com.baeldung.wordtemplate;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public class SampleRenderPolicy implements RenderPolicy {

	@Override
	public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
	    XWPFRun run = ((RunTemplate) eleTemplate).getRun(); 
        String text = "This is sample plugin " + String.valueOf(data);
        run.setText(text, 0);
	}
}
