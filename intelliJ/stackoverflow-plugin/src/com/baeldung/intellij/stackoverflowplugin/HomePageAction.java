package com.baeldung.intellij.stackoverflowplugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class HomePageAction extends AnAction
{
   @Override
   public void actionPerformed(AnActionEvent e)
   {
      BrowserUtil.browse("https://stackoverflow.com/");
   }
}
