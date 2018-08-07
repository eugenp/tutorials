package com.baeldung.intellij.plugin.stackoverflow.action;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.ActionUtil;

/**
 * Action to search for selected text on Stack Overflow.
 */
public class StackOverflowSearch extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        BrowserUtil.browse("https://stackoverflow.com/search?q=" + "spring-boot");
    }
}
