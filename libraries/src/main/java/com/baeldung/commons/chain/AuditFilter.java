package com.baeldung.commons.chain;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;

public class AuditFilter implements Filter {

    @Override
    public boolean postprocess(Context context, Exception exception) {
        // Send notification to customer & bank.
        return false;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        return false;
    }
}