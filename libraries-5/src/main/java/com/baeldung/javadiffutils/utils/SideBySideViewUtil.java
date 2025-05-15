package com.example.diff;

import com.github.difflib.DiffUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SideBySideViewUtil {

    private static final Logger logger = Logger.getLogger(SideBySideViewUtil.class.getName());

    public void display(List<String> original, List<String> revised)
    {
        var patch = DiffUtils.diff(original, revised);
        patch.getDeltas().forEach(delta -> {
        logger.log(Level.INFO,"Change: " + delta.getType());
        logger.log(Level.INFO,"Original: " + delta.getSource().getLines());
        logger.log(Level.INFO,"Revised: " + delta.getTarget().getLines());
        });
    }
}
