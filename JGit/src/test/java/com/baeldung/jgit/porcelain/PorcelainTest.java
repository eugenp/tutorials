package com.baeldung.jgit.porcelain;

import org.junit.Test;

public class PorcelainTest {
    @Test
    public void runSamples() throws Exception {
        // simply call all the samples to see any severe problems with the samples
        AddFile.main(null);
        
        CommitAll.main(null);
        
        CreateAndDeleteTag.main(null);
         
        Log.main(null);    
    }
}
