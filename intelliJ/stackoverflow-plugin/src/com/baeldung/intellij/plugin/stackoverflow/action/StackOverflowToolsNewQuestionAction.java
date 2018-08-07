package com.baeldung.intellij.plugin.stackoverflow.action;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class StackOverflowToolsNewQuestionAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        BrowserUtil.browse("https://stackoverflow.com/questions/ask/");
    }
}
