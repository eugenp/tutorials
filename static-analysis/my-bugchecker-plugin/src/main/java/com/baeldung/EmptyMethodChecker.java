package com.baeldung;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.fixes.SuggestedFix;
import com.google.errorprone.matchers.Description;
import com.sun.source.tree.MethodTree;


@AutoService(BugChecker.class)
@BugPattern(name = "EmptyMethodCheck", summary = "Empty methods should be deleted", severity = BugPattern.SeverityLevel.ERROR)
public class EmptyMethodChecker extends BugChecker implements BugChecker.MethodTreeMatcher {
    @Override
    public Description matchMethod(MethodTree methodTree, VisitorState visitorState) {
        if (methodTree.getBody()
          .getStatements()
          .isEmpty()) {
            return describeMatch(methodTree, SuggestedFix.delete(methodTree));
        }
        return Description.NO_MATCH;
    }
}
