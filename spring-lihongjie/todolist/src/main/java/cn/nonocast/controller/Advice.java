package cn.nonocast.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import cn.nonocast.tag.*;

@ControllerAdvice
public class Advice {
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("shorten", shorten);
	    model.addAttribute("filesize", filesize);
    }

    private ShortenMethod shorten = new ShortenMethod();
	private FileSizeMethod filesize = new FileSizeMethod();
}
