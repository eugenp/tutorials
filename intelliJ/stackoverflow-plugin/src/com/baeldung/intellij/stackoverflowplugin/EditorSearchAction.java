package com.baeldung.intellij.stackoverflowplugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;

public class EditorSearchAction extends AnAction
{
   /**
    * Convert selected text to a URL friendly string.
    * @param e
    */
   @Override
   public void actionPerformed(AnActionEvent e)
   {
      final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
      CaretModel caretModel = editor.getCaretModel();

      if(caretModel.getCurrentCaret().hasSelection())
      {
         String query = caretModel.getCurrentCaret().getSelectedText().replace(' ', '+');
         BrowserUtil.browse("https://stackoverflow.com/search?q=" + query);
      }
   }

   /**
    * Only make this action visible when text is selected.
    * @param e
    */
   @Override
   public void update(AnActionEvent e)
   {
      final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
      CaretModel caretModel = editor.getCaretModel();
      e.getPresentation().setEnabledAndVisible(caretModel.getCurrentCaret().hasSelection());
   }
}
