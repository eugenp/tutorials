package com.baeldung.intellij.stackoverflowplugin;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.impl.ConsoleViewUtil;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.ide.BrowserUtil;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;

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

      // Get the file type so we can properly tag the search
      Language lang = e.getData(CommonDataKeys.PSI_FILE).getLanguage();

      if(caretModel.getCurrentCaret().hasSelection())
      {
         String query = caretModel.getCurrentCaret().getSelectedText().replace(' ', '+') + "+[" + lang.getDisplayName().toLowerCase() + "]";
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
