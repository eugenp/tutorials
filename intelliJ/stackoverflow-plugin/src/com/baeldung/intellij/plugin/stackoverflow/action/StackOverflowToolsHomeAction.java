package com.baeldung.intellij.plugin.stackoverflow.action;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class StackOverflowToolsHomeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        BrowserUtil.browse("https://stackoverflow.com/");
    }
}
