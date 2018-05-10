package com.baeldung.stringisnumeric;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class IsNumericDriver {
    private static IsNumeric isNumeric;
    
    static {
        isNumeric =new IsNumeric();
    }
    
    public static void main(String[] args) {
        LOG.info("Testing all methods...");
        
        boolean res = isNumeric.usingCoreJava("1001");
        LOG.info("Using Core Java : " + res);
        
        res = isNumeric.usingRegularExpressions("1001");
        LOG.info("Using Regular Expressions : " + res);
        
        res =isNumeric.usingNumberUtils_isCreatable("1001");
        LOG.info("Using NumberUtils.isCreatable : " + res);
        
        res =isNumeric.usingNumberUtils_isParsable("1001");
        LOG.info("Using NumberUtils.isParsable : " + res);
        
        res =isNumeric.usingStringUtils_isNumeric("1001");
        LOG.info("Using StringUtils.isNumeric : " + res);
        
        res =isNumeric.usingStringUtils_isNumericSpace("1001");
        LOG.info("Using StringUtils.isNumericSpace : " + res);
    }
}
